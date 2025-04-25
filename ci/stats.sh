#!/bin/bash

set -euo pipefail

readonly PROJECTS=("hello-world:java" "cli:java")
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
  awk 'BEGIN { printf "%-20s %10s %15s %10s\n", "Module", "Size(Mb)", "Size(no gc)", "Diff(Mb)" }'
  for value in "${STATS[@]}"; do
    read -r -a arr <<< "$value"
    if [ ${#arr[@]} -eq 3 ]; then
      awk -v a="${arr[0]}" -v b="${arr[1]}" -v c="${arr[2]}" 'BEGIN { printf "%-20s %10.2f %15.2f %10.2fs\n", a, b/1048576, c/1048576, (b-c)/1048576 }'
    else
      awk -v a="${arr[0]}" -v b="${arr[1]}" 'BEGIN { printf "%-20s %10.2f %15s %10s\n", a, b/1048576, "n/a", "n/a" }'
    fi
  done
}

add_stats() {
  local -r index=$((PROJECTS_COUNT+1))
  STATS[$index]="$1 $(stat --printf="%s" "$2")"
  PROJECTS_COUNT=$index
}

collect_stats() {
  local -r clis_path="cli"
  local -r helloworlds_path="hello-world"
  local -r java_build="build/native/nativeCompile"

  run_gradle "nativeCompile"
  STATS[0]="${STATS[0]} $(stat --printf="%s" "$helloworlds_path/java/$java_build/java")"
  STATS[1]="${STATS[1]} $(stat --printf="%s" "$clis_path/java/$java_build/cli")"

  run_gradle "nativeCompile" "-Pgc=epsilon"
  STATS[0]="${STATS[0]} $(stat --printf="%s" "$helloworlds_path/java/$java_build/java")"
  STATS[1]="${STATS[1]} $(stat --printf="%s" "$clis_path/java/$java_build/cli")"

  ./gradlew -PappLogger=slf4j ":$clis_path:java:nativeCompile"
  add_stats "cli(slf4j)" "$clis_path/java/$java_build/cli"

  (cd "$clis_path/spring-boot" && ./gradlew "nativeCompile")
  add_stats "cli(spring-boot)" "$clis_path/spring-boot/$java_build/cli"

  make -C "$helloworlds_path/c"
  add_stats "c" "$helloworlds_path/c/hello"

  make -C "$helloworlds_path/cpp"
  add_stats "cpp" "$helloworlds_path/c/hello"

  (cd "$helloworlds_path/go/hello" && go build)
  add_stats "go" "$helloworlds_path/go/hello/hello"

  (cd "$helloworlds_path/rust/hello" && cargo build --release)
  add_stats "rust" "$helloworlds_path/rust/hello/target/release/hello"

  # Handled as independent project for https://github.com/graalvm/native-build-tools/issues/70
  (cd "$helloworlds_path/spring-boot-cli" && ./gradlew nativeCompile)
  add_stats "spring-boot-cli" "$helloworlds_path/spring-boot-cli/build/native/nativeCompile/spring-boot-cli"

  (cd "$helloworlds_path/spring-boot-shell" && ./gradlew nativeCompile)
  add_stats "spring-boot-shell" "$helloworlds_path/spring-boot-shell/build/native/nativeCompile/spring-boot-shell"

  (cd "$helloworlds_path/quarkus-cli" && ./gradlew build -Dquarkus.package.type=native)
  add_stats "quarkus" "$helloworlds_path/quarkus-cli/build/quarkus-cli-1.0.0-SNAPSHOT-runner"
}

main() {
  collect_stats
  echo "Result"
  print_stats

# compare_binaries
}

main
