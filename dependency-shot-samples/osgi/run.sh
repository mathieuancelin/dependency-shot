#!/bin/sh
#

clear;
#export DEBUG_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,address=8000"
#mvn clean install;
cd ./test-container/target/dependency-shot-samples-osgi-test-container-1.0-SNAPSHOT-all/dependency-shot-samples-osgi-test-container-1.0-SNAPSHOT/;
java $DEBUG_OPTS -jar bin/felix.jar;
