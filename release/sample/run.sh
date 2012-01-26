#!/bin/sh

VERSION=1.3.1

CLASSPATH=../../lib/scala-library.jar:\
../../lib/mina-core-2.0.0-RC1.jar:\
../../lib/log4j-1.2.14.jar:\
../../lib/slf4j-api-1.5.11.jar:\
../../lib/slf4j-log4j12-1.5.11.jar:\
../scala-amf-lib-${VERSION}.jar:\
../scala-amf-mina-filters-${VERSION}.jar:\
../scala-rpc-${VERSION}.jar:\
sample-server.jar

java -cp $CLASSPATH com.yzh44yzh.scalaAmf.SampleServer
