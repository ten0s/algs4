#!/bin/bash -e

if [[ $# -lt 1 ]]; then
    echo "debug.sh <CLASS> [ARGS...]"
    exit 1
fi

ARGS=${2}
if [[ -f "${1}.java" ]]; then
    expect -c "
set timeout -1
spawn rlwrap jdb ${1} ${ARGS[@]}
send \"stop in ${1}.main\r\"
send \"run\r\"
expect \"main*\"
sleep 1
send \"list\r\"
interact
"
else
    echo "${1}.java not found"
    exit 1
fi
