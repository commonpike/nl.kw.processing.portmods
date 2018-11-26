20181114*pike
# Example Processing 3 Library
Does nothing, just the structure

## Location

This whole folder should go into your "sketchbook location",
also sometimes called the "processing library directory".
You can find its location in the Processing app, in the menu,
under preferences. It's usually in your homedir somewhere.

## Folder structure

If the Library folder is called `ExampleFooLibrary`
 - it must contains a folder `ExampleFooLibrary/library`
 - it must(?) contain a file `ExampleFooLibrarylibrary.properties`
 - it must contain a file `ExampleFooLibrary/library/ExampleFooLibrary.jar`

It may contain lots of other unrelated things.
The 'build' folder in this dir is not required;
This 'README.txt' is not required.

## Import logic

You will notice this library only contains
one jar file, the `./library/ExampleFooLibrary.jar`. 
That jar only contains one java package, the `nl.kw.processing.ExampleBazPackage`.
That package contains two classes, `ExampleBar` and `ExampleQuz`.

If you 'import' the library in the Processing app, all 
it does is write 

`import nl.kw.processing.ExampleBazPackage.*`

in your code. And when you run your code, when compiling,
it tries to read the classes from the jar file in the library folder.

In the jar file, there could be more packages (?). 
Importing it would then cause more import statements
to be added to your code, that's all.

In the library folder, there could also be more jar files. 
For these files, import statements will not be written automatically.
Only the jar file with the exact same name as the library
gets imported automagically.

See also:
https://github.com/processing/processing/wiki/Library-Basics

## Compiling 

Most people will use some IDE like eclipse to generate
the required jar file. But the hard way is

- write *.java files like the ones in ./source, using vi ofcourse:-)
- compile the .java files to .class files 
- zip the .class files to a .jar  

```

cd build/source
vi ExampleBar.java
vi ExampleQuz.java
cd -

cd build/compiled
javac -d . -classpath /path/to/processing/core.jar ../source/*.java
cd -

cd build/
jar -cf ../library/ExampleFooLibrary.jar -C compiled .
cd -
  
  
```

or just take a look at `bin/compile.sh` in this repo.

## Problems 

If you have problems compiling, you may be using the wrong java
version (JRE or JDK). Check what `java -version` and `javac -version` say.

# nl.kw.processing.mods
# nl.kw.processing.mods
