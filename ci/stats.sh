#!/bin/bash

set -euo pipefail

readonly PROJECTS=("base" "app")
#project_name;size;size gc=epsilon
STATS=("${PROJECTS[@]}")
PROJECTS_COUNT="${#STATS[@]}"

compare_binaries() {
  base_size=$(stat --printf="%s" "base/build/native/nativeCompile/base")
  app_size=$(stat --printf="%s" "app/build/native/nativeCompile/app")

  size_increase_kb=$(((app_size - base_size)/1024))
  echo "Size difference: $((size_increase_kb/1024))MB (${size_increase_kb}KB)"
}

run_gradle() {
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
    if [ ${#arr[@]} -eq 3 ]; then
      size_nogc_mb=$(echo "scale=2; ${arr[2]}/1048576" | bc)
      diff=$(echo "scale=2; (${arr[1]}-${arr[2]})/1048576" | bc)
    else
      size_nogc_mb="n/a"
      diff="n/a"
    fi
    echo -e "${arr[0]}\t${size_mb}\t ${size_nogc_mb}\t\t${diff}"
  done
}

add_stats() {
  local -r index=$((PROJECTS_COUNT+1))
  STATS[$index]="$1 $(stat --printf="%s" "$2")"
  PROJECTS_COUNT=$index
  echo "COUNT (after): $PROJECTS_COUNT"
}

collect_stats() {
  run_gradle "nativeCompile"
  STATS[0]="${STATS[0]} $(stat --printf="%s" "base/build/native/nativeCompile/base")"
  STATS[1]="${STATS[1]} $(stat --printf="%s" "app/build/native/nativeCompile/app")"

  run_gradle "nativeCompile" "-Pgc=epsilon"
  STATS[0]="${STATS[0]} $(stat --printf="%s" "base/build/native/nativeCompile/base")"
  STATS[1]="${STATS[1]} $(stat --printf="%s" "app/build/native/nativeCompile/app")"

  local -r base_path="non-java-examples"
  make -C non-java-examples/c
  add_stats "c" "${base_path}/c/hello"
#  STATS[2]="c $(stat --printf="%s" "non-java-examples/c/hello")"

  make -C non-java-examples/cpp
  add_stats "cpp" "${base_path}/c/hello"
#  STATS[3]="cpp $(stat --printf="%s" "non-java-examples/cpp/hello")"

  (cd non-java-examples/go/hello && go build)
  add_stats "go" "${base_path}/go/hello/hello"
#  STATS[4]="Go $(stat --printf="%s" "non-java-examples/go/hello/hello")"

  (cd non-java-examples/rust/hello && cargo build --release)
  add_stats "rust" "${base_path}/rust/hello/target/release/hello"
}

main() {
  collect_stats
  echo "Result"
  print_stats

# compare_binaries
}

main
