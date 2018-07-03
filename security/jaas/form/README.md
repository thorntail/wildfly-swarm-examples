# JAAS Form Auth Sample

This example is identical to JPA, JAX-RS and CDI with Shrinkwrap Example,
with the addition of setting up Java Authentication and Authorization Service(JAAS).

## Run

You can run it many ways:

* mvn package && java -jar ./target/example-jaas-form-swarm.jar
* mvn thorntail:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

http://localhost:8080/

You can see a Form for username/password with the `Login` button.

Input `Penny/password` to get a list of all Employees which is permitted only to users in the 'admin' role.
Restart a browser and input `Sheldon/password` ('user' role) and get an empty list of Employees.
Finally, restart a browser and input `Amy/password` ('guest' role) and get a 'Forbidden' response.

