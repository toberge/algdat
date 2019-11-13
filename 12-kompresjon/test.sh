#!/usr/bin/env bash

export CLASSPATH="$CLASSPATH:../out/production/12-kompresjon/"

failed=0
files=(sample diverse.{pdf,txt} opg12.{pdf,txt} "src/HuffmanToolkit.java" urandom)
for file in ${files[*]}
do
  echo "-----$file-----"
  if [ ! -f $file ]
  then
    echo "NO SUCH FILE, skipping ahead..."
    echo
    continue
  fi

  java Compressor "$file" "$file.huff"
  java Decompressor "$file.huff" "$file.huff.unhuff"

  echo "- - - - - - - - - -"
  echo "Diffing $file:"
  if diff "$file" "$file.huff.unhuff"
  then
    echo "Files are intact."
  else
    echo "THERE IS A DIFFERENCE"
    failed=1
  fi

  uncompressed=$(stat -c %s "$file")
  compressed=$(stat -c %s "$file.huff")
  echo "Original   $uncompressed bytes"
  echo "Compressed $compressed bytes"
  echo "Difference $(awk "BEGIN { printf \"%0.2f\", (($uncompressed - $compressed) / $uncompressed) * 100 }")%"
  echo
done

if [ $failed -eq 1 ]
then
  echo "ONE OR MORE TEST(S) FAILED"
else
  echo 'Tests complete, all good ¯\_(ツ)_/¯'
fi
