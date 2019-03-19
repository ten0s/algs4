#!/bin/bash

if [[ $# -ne 1 ]]; then
    echo "test.sh <CLASS>"
    exit 1
fi

if [[ -f "${1}Test.class" ]]; then
    java -cp . org.junit.runner.JUnitCore "${1}Test"
else
    echo "${1}Test.class not found"
    exit 1
fi
