#!/bin/sh

# https://github.com/processing/processing/wiki/Library-Guidelines

OPWD=`pwd`
cd `dirname $0`/..


if [ "$LIBNAME" = "" ]; then
	LIBNAME=$(basename "$PWD")
	read -p "What is the libraries name [$LIBNAME]? " libname
	if [ "$libname" = "" ]; then
		libname=$LIBNAME
	fi
else
	libname=$LIBNAME
fi

LIBDIRNAME=$(basename "$PWD")

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

mkdir -p dist

echo "Removing old zip .."
touch dist/$libname.zip
rm dist/$libname.zip

# zipping. stepping up 1 level 
# to include the directory in the zip


echo "Zipping everything to dist/$libname.zip .."

cd ../
zip -r $LIBDIRNAME/dist/$libname.zip $FILES
	
# library.properties

if [ -f "library.properties" ]; then
	cp -v $LIBDIRNAME/library.properties $LIBDIRNAME/dist/$libname.txt
fi	
	
	
echo All done.


cd "$OPWD" &>/dev/null
