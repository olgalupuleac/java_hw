#!/bin/bash

cd term2
ls -l
echo "end of list"
for FILE in `ls -l`
do 
    echo "$FILE"
    if test -d $FILE
    then
      cd "$FILE"
      chmod +x gradlew
      ./gradlew build
      if [ $? -ne 0 ]; then
        exit 1
      fi
   fi
done