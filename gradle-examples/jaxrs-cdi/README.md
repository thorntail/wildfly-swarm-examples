# Gradle plugin example

Beginning of a Gradle plugin example.


## Build

`./gradlew clean thorntail-package`


##Run

`java -jar ./build/libs/example-gradle-thorntail.jar`


## Configuration

If you're using the default `application` plugin, The `thorntail-maven-plugin`
can piggy-back on the `mainClassName` build property.

Else, you can set `swarm.mainClassName` to specify the main-class for you
application.

