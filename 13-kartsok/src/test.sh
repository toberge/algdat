#!/usr/bin/env bash

export CLASSPATH="$CLASSPATH:../../out/production/13-kartsok/:../../out/production/ovinger/"

dir="data/norden"

# you moron, no -d $'\t' needed, tab is default delimiter
# just like ed is the default text editor \s
from=$(grep Trondheim < ../${dir}/interessepkt.txt | cut -f 1)
to=$(grep Oslo < ../${dir}/interessepkt.txt | cut -d $'\t' -f 1)

echo "From ${from} to ${to}"

java MapSearch ../${dir}/noder.txt ../${dir}/kanter.txt ${from} ${to}

# -----things -------------
#java MapSearch ../${dir}/noder.txt ../${dir}/kanter.txt 786240 2603334

# helping the randi
# From 2460904 to 2419175

# the my way
#------ A* ------
#Milliseconds per round: 6.799000e+03 | (1 rounds total)
#Nodes inspected: 3781131
#Time in centiseconds: 6847534
#Time: 19 hours, 1 minutes, 15 seconds
#------ Dijkstra ------
#Milliseconds per round: 6.934000e+03 | (1 rounds total)
#Nodes inspected: 5449564
#Time in centiseconds: 6847534
#Time: 19 hours, 1 minutes, 15 seconds

# the randi way
#------ A* ------
#Milliseconds per round: 1.971000e+03 | (1 rounds total)
#Nodes inspected: 3781093
#Time in centiseconds: 6847534
#Time: 19 hours, 1 minutes, 15 seconds
#------ Dijkstra ------
#Milliseconds per round: 2.005000e+03 | (1 rounds total)
#Nodes inspected: 5450091
#Time in centiseconds: 6849417
#Time: 19 hours, 1 minutes, 34 seconds

# aka wtf is up here
