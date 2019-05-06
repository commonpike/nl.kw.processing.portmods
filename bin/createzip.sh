#!/bin/sh

# https://github.com/processing/processing/wiki/Library-Guidelines

OPWD=`pwd`
cd `dirname $0`/..

LIBNAME=$(basename "$PWD")
LIBDIRNAME=$LIBNAME

echo

read -p "What is the libraries name [$LIBNAME]? " libname
if [ "$libname" = "" ]; then
	libname=$LIBNAME
fi


FILES=""

# required files

if [ ! -d "src" ]; then
	echo
	echo "The 'src' directory is missing;"
	echo "This should contain your .java files."
	echo "aborting"
	exit 1;
else
	FILES="$FILES $LIBDIRNAME/src"
fi

if [ ! -d "library" ]; then
	echo
	echo "The 'library' directory is missing; "
	echo "This should contain your .jar files."
	echo "aborting"
	exit 1;
else
	FILES="$FILES $LIBDIRNAME/library"
fi

if [ ! -d "reference" ]; then
	echo
	echo "The 'reference' directory is missing; aborting"
	echo "This should contain your javadoc files."
	echo "aborting"
	exit 1;
else
	FILES="$FILES $LIBDIRNAME/reference"
fi

if [ ! -f "library.properties" ]; then
	echo
	echo "The 'library.properties' directory is missing; aborting"
	echo "aborting"
	exit 1;
else
	FILES="$FILES $LIBDIRNAME/library.properties"
fi

# optional files

if [ -d "examples" ]; then
	FILES="$FILES $LIBDIRNAME/examples"
fi

if [ -d "docs" ]; then
	FILES="$FILES $LIBDIRNAME/docs"
fi

if [ -f "README.md" ]; then
	FILES="$FILES $LIBDIRNAME/README.md"
fi

# all good

echo "Some cleanup .."
find . -name '.DS_Store' -exec rm -v {} \;

echo "Removing old zip .."
touch dist/$libname.zip
rm dist/$libname.zip

echo "Zipping everything to dist/$libname.zip .."


cd ../
zip -r $LIBDIRNAME/dist/$libname.zip $FILES
	
	
read -n 1 -p "Copy library.properties next to zip [Y/n]? " answer
echo
if [ "$answer" != "${answer#[Nn]}" ] ;then
	echo "Skipping props file ..."
else
	cp -v $LIBDIRNAME/library.properties $LIBDIRNAME/dist/$libname.txt
fi	
	
echo All done.


cd "$OPWD" &>/dev/null
