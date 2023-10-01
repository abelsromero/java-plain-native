package main

import (
	"os"
)

func ExtractName() *string {
	if len(os.Args) == 1 {
		return nil
	} else {
		return &os.Args[1]
	}
}
