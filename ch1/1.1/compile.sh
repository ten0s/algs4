#!/bin/bash -e

if [[ $# -ne 1 ]]; then
    echo "compile.sh <CLASS>"
    exit 1
fi

if [[ -f "${1}.java" ]]; then
    javac -cp . "${1}.java"
else
    echo "${1}.java not found"
    exit 1
fi

if [[ -f "${1}Test.java" ]]; then
    javac -cp . "${1}Test.java"
else
    exit 0
fi
