# Camel CXF JAX-WS Example

This example uses camel-cxf to expose a JAX-WS consumer.

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

* mvn package && java -jar ./target/example-camel-cxf-jaxws-swarm.jar
* mvn thorntail:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

Once the Swarm container has started, you can view the CXF web service endpoint WSDL by hitting:

  http://localhost:8080/camel/greeting?wsdl
