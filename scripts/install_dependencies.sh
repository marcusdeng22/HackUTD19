#!/usr/bin/env bash

if [ -d venvProcurement ]; then
    source hackUTDenv/bin/activate
fi

python3 -m pip install -r requirements.txt
