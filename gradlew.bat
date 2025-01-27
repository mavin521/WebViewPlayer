@ECHO OFF

SET DIR=%~dp0
SET JAVA_EXE=
SET DEFAULT_JVM_OPTS=

"%DIR%gradle\wrapper\gradle-wrapper.jar" %*
