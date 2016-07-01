# Camel CXF JAX-WS Example

This example uses camel-cxf to expose a JAX-WS consumer.

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

* mvn package && java -jar ./target/example-camel-cxf-jaxws-swarm.jar
* mvn wildfly-swarm:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

Once the Swarm container has started, you can view the CXF web service endpoint WSDL by hitting:

  http://localhost:8080/camel/greeting?wsdl
