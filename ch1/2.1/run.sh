#!/bin/bash -e

if [[ $# -lt 1 ]]; then
    echo "run.sh <CLASS> [ARGS...]"
    exit 1
fi

ARGS=${2}
if [[ -f "${1}.java" ]]; then
    java ${1} ${ARGS[@]}
else
    echo "${1}.java not found"
    exit 1
fi
