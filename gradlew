#!/usr/bin/env sh

# Find the gradlew script location
DIR=$(cd "$(dirname "$0")" && pwd)
exec "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
