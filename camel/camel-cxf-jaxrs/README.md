# Camel CXF JAX-RS Example

This example uses camel-cxf to expose a JAX-RS REST consumer.

## Project `pom.xml`

The project adds a `<plugin>` to configure `thorntail-maven-plugin` to
create the runnable `.jar`.

    <plugin>
      <groupid>io.thorntail</groupId>
      <artifactId>thorntail-maven-plugin</artifactId>
    </plugin>

To define the needed parts of Thorntail, the following dependencies are added

    <dependency>
        <groupid>io.thorntail</groupId>
        <artifactId>camel-cxf</artifactId>
    </dependency>

This dependency provides Camel and CXF APIs to your application, so the
project does *not* need to specify those.

## Run

You can run it many ways:

* java -jar ./target/example-camel-cxf-jaxrs-swarm.jar
* mvn thorntail:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

Once the Swarm container has started, you can test the Camel CXF application by hitting the following HTTP endpoint.

    http://localhost:8080/camel/greeting/hello/bob

You should see a response of 'Hello bob'
