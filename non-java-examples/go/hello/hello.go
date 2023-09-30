package main

import (
	"fmt"
	"os"
)

func main() {
    var name = ExtractName()
    if name == nil {
		fmt.Println("Hello World!")
	} else {
		fmt.Println("Hello", os.Args[1], "!")
	}
}
