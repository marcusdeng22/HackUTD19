#!/usr/bin/env bash

if [ -d venvProcurement ]; then
    source hackUTDenv/bin/activate
fi

cd src/python/studyLoc
python3 server.py
