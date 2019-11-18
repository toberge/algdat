#!/usr/bin/env bash

cd ../data

for area in {island,norden}
do
    mkdir ${area}
    pushd ${area}

    for url in http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/${area}/{noder,kanter,interessepkt}.txt
    do
        wget ${url}
    done

    popd
done
