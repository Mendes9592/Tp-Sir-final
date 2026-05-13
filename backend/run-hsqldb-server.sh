#!/bin/bash
HSQLDB_JAR="$HOME/.m2/repository/org/hsqldb/hsqldb/2.7.2/hsqldb-2.7.2.jar"
mkdir -p data
cd data
java -cp "$HSQLDB_JAR" org.hsqldb.Server
