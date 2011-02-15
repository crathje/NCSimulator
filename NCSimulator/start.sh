#!/bin/bash

ISX64=`uname -a | grep -c "x86_64"`
SIMJAR="NCSimulator.jar"
LIBFILE="lib/librxtxSerial.so"
JAVA=`which java`

if [ "${JAVA}" = "" ]; then
	# tell the user where to get java
	echo "Could not find Java."
	echo "Please install Java Runtime Environment from http://www.java.com/download/ and add it to the PATH environment variable"
	exit
fi

if [ ! -x ${JAVA} ]; then
	echo "${JAVA} not executable. Too bad..."
	exit
fi

if [ ! -x $SIMJAR ]; then
	chmod +x $SIMJAR
fi

if [ `uname` = "Darwin" ]; then
	echo "MaxOS"
	cp lib/rxtx.org/mac-10.5/librxtxSerial.jnilib lib/
	LIBFILE="lib/librxtxSerial.jnilib"
else
	if [ $ISX64 -gt 0 ]; then
		echo "x64"
		cp lib/rxtx.org/x86_64-unknown-linux-gnu/librxtxSerial.so lib/
	else
		echo "i686"
		cp lib/rxtx.org/i686-pc-linux-gnu/librxtxSerial.so lib/
	fi
fi

if [ ! -x $LIBFILE ]; then
	chmod +x $LIBFILE
fi

${JAVA} -Djava.library.path=lib -jar $SIMJAR

