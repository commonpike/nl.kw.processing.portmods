#!/bin/sh

cd `dirname $0`/..

if [ "$COREJAR" = "" ]; then
	COREJAR=/3rdparty/Processing.app/Contents/Java/core.jar
	read -e -p "Where is Processings core.jar [$COREJAR]? " corejar
	if [ "$corejar" = "" ]; then
		corejar=$COREJAR
	fi
else
	corejar=$COREJAR
fi


if [ "$LIBNAME" = "" ]; then
	LIBNAME=$(basename "$PWD").jar
	read -p "What is the libraries name [$LIBNAME]? " libname
	if [ "$libname" = "" ]; then
		libname=$LIBNAME
	fi
else
	libname=$LIBNAME
fi




# vi src/ExampleBar.java
# vi src/folder/foo/whatever/ExampleQuz.java

echo
echo 'Compiling src/**.java to ./build/*.class ..'

find src -name "*.java" -print0 | xargs -0 \
 javac -d build -classpath "$corejar"

  
if [ $? -eq 0 ]; then
  
  echo "Jarring ./build/**.class to ./library/$libname.jar .."
	jar -cf library/$libname.jar -C build .

	echo All done.
	echo

fi

cd - &>/dev/null
