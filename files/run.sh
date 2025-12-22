#!/bin/bash

if [[ -f "SerialChecker.py" ]]; then
    python SerialChecker.py

elif [[ -f "SerialChecker.class" ]]; then
    java SerialChecker

elif [[ -x "SerialChecker" ]]; then
    ./SerialChecker

else
    echo "No runnable SerialChecker file found (Python, Java, or compiled binary)."
    exit 1
fi
