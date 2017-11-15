#!/bin/bash

for FILE in `ls -l`
do
    if test -d $FILE
    then
      if [ "$FILE" != "hw1" ] && [ "$FILE" != "test1" ]
      then
        echo "$FILE"
        cd "$FILE"
        chmod +x gradlew
        ./gradlew build
      fi
   fi
done