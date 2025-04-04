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
  awk 'BEGIN { printf "%-20s %-12s %-15s %-10s\n", "module", "size(Mb)", "size(no gc)", "dif(Mb)" }'
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
    awk -v a="${arr[0]}" -v b="${size_mb}" -v c="${size_nogc_mb}" -v d="${diff}" 'BEGIN { printf "%-20s %-10s %-10s %-10s\n", a, b, c, d }'
  done
}

add_stats() {
  local -r index=$((PROJECTS_COUNT+1))
  STATS[$index]="$1 $(stat --printf="%s" "$2")"
  PROJECTS_COUNT=$index
}

collect_stats() {
  run_gradle "nativeCompile"
  STATS[0]="${STATS[0]} $(stat --printf="%s" "base/build/native/nativeCompile/base")"
  STATS[1]="${STATS[1]} $(stat --printf="%s" "app/build/native/nativeCompile/app")"

  run_gradle "nativeCompile" "-Pgc=epsilon"
  STATS[0]="${STATS[0]} $(stat --printf="%s" "base/build/native/nativeCompile/base")"
  STATS[1]="${STATS[1]} $(stat --printf="%s" "app/build/native/nativeCompile/app")"

  local -r module="app"
  ./gradlew -PappLogger=slf4j ":$module:nativeCompile"
  add_stats "app(slf4j)" "$module/build/native/nativeCompile/app"

  local -r base_path="examples"
  make -C "$base_path/c"
  add_stats "c" "$base_path/c/hello"

  make -C "$base_path/cpp"
  add_stats "cpp" "$base_path/c/hello"

  (cd "$base_path/go/hello" && go build)
  add_stats "go" "$base_path/go/hello/hello"

  (cd "$base_path/rust/hello" && cargo build --release)
  add_stats "rust" "$base_path/rust/hello/target/release/hello"

  # Handled as independent project for https://github.com/graalvm/native-build-tools/issues/70
  (cd "$base_path/spring-boot-cli" && ./gradlew nativeCompile)
  add_stats "spring-boot-cli" "$base_path/spring-boot-cli/build/native/nativeCompile/spring-boot-cli"

  (cd "$base_path/spring-boot-shell" && ./gradlew nativeCompile)
  add_stats "spring-boot-shell" "$base_path/spring-boot-shell/build/native/nativeCompile/spring-boot-shell"

#  (cd "$base_path/quarkus-cli" && ./gradlew build -Dquarkus.package.type=native)
#  add_stats "quarkus" "$base_path/quarkus-cli/build/quarkus-cli-1.0.0-SNAPSHOT-runner"
}

main() {
  collect_stats
  echo "Result"
  print_stats

# compare_binaries
}

main
