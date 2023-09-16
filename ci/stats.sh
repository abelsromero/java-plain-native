#!/bin/bash

set -euo pipefail

list_paths() {
  ls -lah base/build/native/nativeCompile/
  ls -lah app/build/native/nativeCompile/
}

compare_binaries() {
  base_size=$(stat --printf="%s" base/build/native/nativeCompile/hello)
  app_size=$(stat --printf="%s" app/build/native/nativeCompile/my-app)

  size_increase_kb=$(((app_size - base_size)/1024))
  echo "Size difference: $((size_increase_kb/1024))MB (${size_increase_kb}KB)"
}

main() {
  list_paths
  compare_binaries
}

main
