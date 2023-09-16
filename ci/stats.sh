#!/bin/bash

set -euo pipefail


list_module_stats() {
  echo "Stats: $1"
  ls -lahp "$1/build/native/nativeCompile/" | grep -v /$
}

compare_binaries() {
  base_size=$(stat --printf="%s" base/build/native/nativeCompile/hello)
  app_size=$(stat --printf="%s" app/build/native/nativeCompile/my-app)

  size_increase_kb=$(((app_size - base_size)/1024))
  echo "Size difference: $((size_increase_kb/1024))MB (${size_increase_kb}KB)"
}

main() {
  list_module_stats "base"
  list_module_stats "app"
  compare_binaries
}

main
