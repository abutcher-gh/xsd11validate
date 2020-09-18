# XSD 1.1 Validator

## Background

I couldn't find a convenient open-source command-line tool to validate XML files against XSD 1.1 schemas, so I knocked up a very simple one with [Xerces2-J](http://xerces.apache.org/xerces2-j/).

## Usage

Standalone binary package on PATH
```sh
  xsd11validate schema.xsd [file.xml...]
```
or with existing Java installation (jre>=9 or jre=8 respectively)
```sh
  java -jar xsd11validate.jar schema.xsd [file.xml...]
  java -jar xsd11validate.jre8.jar schema.xsd [file.xml...]
```

The program returns 0 with no output if there are no errors, or the error count with output to `stderr` on failure.

## Download

Go to [latest release](https://github.com/abutcher-gh/xsd11validate/releases/latest).

## Build to run with installed JVM

This can be built for Java 8 with
```sh
  ant -buildfile build.jre8.xml jar
```
or for Java 9 and above with
```sh
  ant jar
```

For convenience of execution, the jars resulting from the build are java-standalone and require only an appropriate JRE (either >=9 or 8 depending on the build used).

The built jars are about 1.2M in size.

## Build to run standalone

In the case of the Java 9+ build, it can also be build as a completely standalone binary package with it's own JRE thanks to [jlink](https://docs.oracle.com/javase/9/tools/jlink.htm).

```sh
  ant standalone
```

I've run the standlone jlink build on i686 and x64 Linux hosts built via OpenJDK-10 and OpenJDK-11 respectively.  The built directory is about 32M in size.

The only dependency is the version of the C library used by the included JVM.
