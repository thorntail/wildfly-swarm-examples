# JAAS Basic Auth Sample

This example is identical to JPA, JAX-RS and CDI with Shrinkwrap Example,
with the addition of setting up Java Authentication and Authorization Service(JAAS).

## Run

You can run it many ways:

* mvn package && java -jar ./target/example-jaas-basic-swarm.jar
* mvn wildfly-swarm:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

http://localhost:8080/

You can see a Basic access authentication dialog for username/password, and then input `Penny/password` to get Employees resource.