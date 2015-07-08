## Run Wildfly-Swarm in Docker

You need to have Docker and docker-compose installed. The Dockerfile downloads Maven version 3.3.3 and builds the project inside the container.
 
### Building the Docker image

You build the Docker image with the command: `docker-compose build`

### Running Docker with a Wildfly-Swarm microservice inside

You run the Docker container and start the Wildfly-Swarm microservice (a simple JAX-RS application) with the following command: `docker-compose up` 

### Inspect your running Docker containers

From a terminal run: `docker ps` and you should see something like: 
````
hota@local.husbanken.no@hota-devpc ~ $ docker ps
CONTAINER ID        IMAGE                      COMMAND                CREATED              STATUS              PORTS                    NAMES
c618ed118938        swarmdocker_swarm:latest   "java -jar /source/t   About a minute ago   Up About a minute   0.0.0.0:8080->8080/tcp 
````
Now try to `curl` the resource running inside Docker:
````
hota@local.husbanken.no@hota-devpc ~ $ curl localhost:8080/resource
````
Result should be the following:
````
bar
````








