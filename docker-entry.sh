#!/usr/bin/env bash

echo -e "\t Starting PCari API server..."
export JAVA_HOME=/root/.sdkman/candidates/java/current
./gradlew bootRun

