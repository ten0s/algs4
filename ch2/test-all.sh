#!/bin/bash

for path in `find -name '*Test.java'`; do
    file=${path##*/}
    test=${file%Test.java}
    echo "Running ${test} tests"
    echo "==="
    make --silent test CLASS=${test};
done
