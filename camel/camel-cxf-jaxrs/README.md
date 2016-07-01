# Camel CXF JAX-RS Example

This example uses camel-cxf to expose a JAX-RS REST consumer.

## Project `pom.xml`

The project adds a `<plugin>` to configure `wildfly-swarm-plugin` to
create the runnable `.jar`.

    <plugin>
      <groupId>org.wildfly.swarm</groupId>
      <artifactId>wildfly-swarm-plugin</artifactId>
    </plugin>

To define the needed parts of WildFly Swarm, the following dependencies are added

    <dependency>
        <groupId>org.wildfly.swarm</groupId>
        <artifactId>camel-cxf</artifactId>
    </dependency>

This dependency provides Camel and CXF APIs to your application, so the
project does *not* need to specify those.

## Run

You can run it many ways:

* java -jar ./target/example-camel-cxf-jaxrs-swarm.jar
* mvn wildfly-swarm:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

Once the Swarm container has started, you can test the Camel CXF application by hitting the following HTTP endpoint.

    http://localhost:8080/camel/greeting/hello/bob

You should see a response of 'Hello bob'
