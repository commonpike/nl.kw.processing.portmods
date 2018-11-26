#!/bin/sh

cd `dirname $0`/..

LIBNAME=$(basename $(dirname "$PWD")).jar
COREJAR=/3rdparty/Processing.app/Contents/Java/core.jar

echo

read -p "Where is Processings core.jar [$COREJAR]? " corejar
if [ "$corejar" = "" ]; then
	corejar=$COREJAR
fi

read -p "What is the libraries name [$LIBNAME]? " libname
if [ "$libname" = "" ]; then
	libname=$LIBNAME
fi

#vi source/ExampleBar.java
#vi source/ExampleQuz.java

echo 'Compiling ./source/*.java to ./compiled/*.class ..'

javac -d compiled -classpath "$corejar" source/*.java
  
if [ $? -eq 0 ]; then
  
  echo "Jarring ./compiled/*.class to ../library/$libname.."
	jar -cf ../library/$libname -C compiled .

	echo All done.
	echo

fi

cd - &>/dev/null
