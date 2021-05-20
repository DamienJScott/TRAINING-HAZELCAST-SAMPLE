#!/bin/sh

java -cp hazelcast-mancenter-3.12.9.war com.hazelcast.webmonitor.cli.MCConfCommandLine "$@"
