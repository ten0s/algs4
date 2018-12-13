#!/bin/bash

for path in `find -name '*.java'`; do
    file=${path##*/}
    class=${file%.java}
    make --silent compile CLASS=${class};
done
