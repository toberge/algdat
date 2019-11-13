#!/usr/bin/env bash

# just static size cuz whatever, five megs of entropic data
dd if=/dev/urandom of=urandom bs=1M count=5
