#!/bin/bash

cd term2
for FILE in `ls -l`
do
    if test -d $FILE
    then
      cd "$FILE"
      chmod +x gradlew
      ./gradlew build
   fi
done