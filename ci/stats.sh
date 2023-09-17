#!/bin/bash

set -euo pipefail

readonly PROJECTS=("base" "app")
#project_name;size;size gc=epsilon
STATS=("${PROJECTS[@]}")

compare_binaries() {
  base_size=$(stat --printf="%s" "base/build/native/nativeCompile/base")
  app_size=$(stat --printf="%s" "app/build/native/nativeCompile/app")

  size_increase_kb=$(((app_size - base_size)/1024))
  echo "Size difference: $((size_increase_kb/1024))MB (${size_increase_kb}KB)"
}

run_task() {
  for project in ${PROJECTS[@]}; do
    if [ $# -eq 1 ]; then
      ./gradlew ":${project}:clean" ":${project}:${1}"
    else
      ./gradlew ":${project}:clean" ":${project}:${1}" "$2"
    fi
  done
}

print_stats() {
  echo "== STATS"
  echo -e "module\tsize(Mb) size(no gc)\tdif(Kb)"
  for value in "${STATS[@]}"; do
    read -r -a arr <<< "$value"
    size_mb=$(echo "scale=2; ${arr[1]}/1048576" | bc)
    size_nogc_mb=$(echo "scale=2; ${arr[2]}/1048576" | bc)
    diff=$(echo "scale=2; (${arr[1]}-${arr[2]})/1048576" | bc)
    echo -e "${arr[0]}\t${size_mb}\t ${size_nogc_mb}\t\t${diff}"
  done
}

collect_stats() {
  run_task "nativeCompile"
  STATS[0]="${STATS[0]} $(stat --printf="%s" "base/build/native/nativeCompile/base")"
  STATS[1]="${STATS[1]} $(stat --printf="%s" "app/build/native/nativeCompile/app")"

  run_task "nativeCompile" "-Pgc=epsilon"
  STATS[0]="${STATS[0]} $(stat --printf="%s" "base/build/native/nativeCompile/base")"
  STATS[1]="${STATS[1]} $(stat --printf="%s" "app/build/native/nativeCompile/app")"
}

main() {
  collect_stats
  echo "Result"
  print_stats

# compare_binaries
}

main
