#!/bin/bash

cd term2
ls
echo "end of list"
for FILE in `ls`
do 
    echo "$FILE"
    if  [ -d "${FILE}" || "$FILE" != "test1" ] ; then
	  echo "again"
	  echo "$FILE"
      cd "$FILE"
      chmod +x gradlew
      ./gradlew build --stacktrace
      if [ $? -ne 0 ]; then
        exit 1
      fi
	  cd ..
    else 
	   echo "fail"
	fi
done