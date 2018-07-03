# Camel CDI Example

This example uses camel-cdi for enabling CDI beans to participate in a Camel route.

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
        <artifactId>camel-cdi</artifactId>
    </dependency>

This dependency provides Camel and CDI APIs to your application, so the
project does *not* need to specify those.

## Run

You can run it many ways:

* mvn package && java -jar ./target/example-camel-cdi-swarm.jar
* mvn thorntail:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

Once the Swarm container has started, you can test the Camel CDI application by hitting the following HTTP endpoint.

    http://localhost:8080/hello?name=bob

You should see a response of 'Hello bob'
