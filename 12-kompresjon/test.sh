#!/usr/bin/env bash

export CLASSPATH="$CLASSPATH:../out/production/12-kompresjon/"

FILES=(sample diverse.{pdf,txt} opg12.{pdf,txt})
for file in ${FILES[*]}
do
  echo "-----$file-----"
  java Compressor "$file" "$file.huff"
  java Decompressor "$file.huff" "$file.huff.unhuff"
  echo "Diffing $file:"
  diff "$file" "$file.huff.unhuff"
  uncompressed=$(stat -c %s "$file")
  compressed=$(stat -c %s "$file.huff")
  echo "Original    $uncompressed bytes"
  echo "Compressed  $compressed bytes"
  echo "Difference $(((compressed * 100) / (uncompressed * 100)))%"
done
#java Compressor sample sample.huff
#java Decompressor sample.huff sample.huff.unhuff
