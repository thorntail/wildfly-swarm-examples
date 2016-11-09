# Camel JMS Example

This example uses camel-jms to produce and consume messages via the messaging subsystem.

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
        <artifactId>camel-full</artifactId>
    </dependency>

This dependency provides Camel and JMS APIs to your application, so the
project does *not* need to specify those.

## Run

You can run it many ways:

* mvn package && java -jar ./target/example-camel-jms-swarm.jar
* mvn wildfly-swarm:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

Once the Swarm container has started, you can test the Camel JMS application by hitting the following HTTP endpoint.

    http://localhost:8080/hello?name=bob

Invoking this endpoint triggers a Camel route to place a string message of 'Hello bob' onto an in-memory JMS queue named TestQueue.

A second Camel route consumes messages from TestQueue and logs their contents to the console. In this example you 
should see a message like the following output to the console:

    TestQueue received message: Hello bob
