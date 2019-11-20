#!/usr/bin/env bash

export CLASSPATH="$CLASSPATH:../../out/production/13-kartsok/:../../out/production/ovinger/"

dir="data/norden"

# you moron, no -d $'\t' needed, tab is default delimiter
# just like ed is the default text editor \s
from=$(grep Trondheim < ../${dir}/interessepkt.txt | cut -f 1)
to=$(grep Oslo < ../${dir}/interessepkt.txt | cut -d $'\t' -f 1)

java MapSearch ../${dir}/noder.txt ../${dir}/kanter.txt ${from} ${to}
