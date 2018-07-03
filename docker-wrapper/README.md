# Run a WildFly-Swarm example in Docker

You need to have docker and docker-compose installed, and the Thorntail example you want to bundle in a
Docker image needs to be built with Maven first.

## Building the Docker image

If we wanted to build the `jpa-jaxrs-cdi` example we would run the following:

`./build.sh ../jpa-jaxrs-cdi/target/wildfly-swarm-example-jpa-jaxrs-cdi-swarm.jar`

This will copy the `jar` file of the `jpa-jaxrs-cdi` example to this directory, add it to the Docker image and build it.

You can use `build.sh` with any of the examples by passing a different path to a Thorntail jar into the script.

## Running Docker image

You can run the Docker container and start the Wildfly-Swarm example with the following commands:

`docker-compose up`

OR

`docker run -p 8080:8080 dockerwrapper_wildflyswarm`

## Inspect your running Docker containers

From a terminal run `docker ps` and you should see something like:

````
CONTAINER ID        IMAGE                                COMMAND                CREATED              STATUS              PORTS                    NAMES
c618ed118938        dockerwrapper_wildflyswarm:latest    "java -jar /opt/wild   About a minute ago   Up About a minute   0.0.0.0:8080->8080/tcp   dockerwrapper_wildflyswarm_1
````

## Use

    http://localhost:8080/

