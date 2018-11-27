#!/bin/sh

cd `dirname $0`/..

COREJAR=/3rdparty/Processing.app/Contents/Java/core.jar

echo

read -p "Where is Processings core.jar [$COREJAR]? " corejar
if [ "$corejar" = "" ]; then
	corejar=$COREJAR
fi


echo 'Document src/*.java to ./reference/*html ..'

find src -name "*.java" -print0 | xargs -0 \
 javadoc -d reference -classpath "$corejar"

  
echo All done.


cd - &>/dev/null
