#!/bin/bash
cmake .
make
c=$(pwd)
lib=$c/lib/libJavaBoolector.so
resources=$c/../java/src/main/resources

if [ ! -d $resources ]
then mkdir $resources
fi

cp $lib $resources/
