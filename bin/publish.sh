#!/bin/sh

XPWD=`pwd`
cd `dirname $0`/..

echo Publishing ...


echo --------------------


read -n 1 -p "Compile .java files into .class and .jar [Y/n]? " answer
echo
if [ "$answer" != "${answer#[Nn]}" ] ;then

	echo Skipping compile ...
	echo
	
else

	bin/compile.sh
	
	
fi

echo --------------------


read -n 1 -p "Generate new javadoc [Y/n]? " answer
echo
if [ "$answer" != "${answer#[Nn]}" ] ;then

	echo Skipping doc ...
	echo
	
else

	bin/javadoc.sh
	
fi

echo --------------------


read -n 1 -p "Create zip for publishing [Y/n]? " answer
echo
if [ "$answer" != "${answer#[Nn]}" ] ;then

	echo Skipping zip ...
	echo
	
else

	bin/createzip.sh
	
	
fi

echo --------------------


if [ -d ".git" ]; then

	read -n 1 -p "Update git [Y/n]? " answer
	echo
	if [ "$answer" != "${answer#[Nn]}" ] ;then
		
		echo Skipping git ...	
		echo
		
	else
	
		git add -u :/
		git commit
		
		BRANCH=`git branch | grep -e "^*" | cut -d' ' -f 2`
		read -n 1 -p "Push $BRANCH too [Y/n]? " answer
		echo
		if [ "$answer" != "${answer#[Nn]}" ] ;then
			echo Skipping push ...
		else 
			REMOTE="origin"
			read -p "Git remote [$REMOTE]? " answer
			if [ "$answer" != "" ] ;then
				REMOTE=$answer
			fi
			git push $REMOTE $BRANCH
		fi
		
		
	fi
fi

echo --------------------

cd "$XPWD" &>/dev/null
echo "All done."

	