#!/bin/bash

cd term2
for FILE in `ls -l`
do
    if test -d $FILE
    then
      cd "$FILE"
      echo "$FILE"
      chmod +x gradlew
      ./gradlew build
      if [ $? -ne 0 ]; then
        exit 1
      fi
   fi
done