#!/bin/sh

# prisonerjohn+p5@gmail.com
# https://github.com/processing/processing/wiki/Library-Guidelines

cd `dirname $0`/..

LIBNAME=$(basename "$PWD")

echo

read -p "What is the libraries name [$LIBNAME]? " libname
if [ "$libname" = "" ]; then
	libname=$LIBNAME
fi

echo "Removing old zip .."
touch dist/$libname.zip
rm dist/$libname.zip

echo "Some cleanup .."
find . -name '.DS_Store' -exec rm -v {} \;

echo "Zipping everything to dist/$libname.zip .."
zip -r dist/$libname.zip \
	src \
	library \
	reference \
	examples \
	library.properties \
	README.md	\
	docs
	

  
echo All done.


cd - &>/dev/null
