# Messaging & Clustering

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/THORN

## Project `pom.xml`

The project is a normal maven project with `jar` packaging, not `war`.

    <packaging>jar</packaging>

The project adds a `<plugin>` to configure `thorntail-maven-plugin` to
create the runnable `.jar`.

    <plugin>
      <groupid>io.thorntail</groupId>
      <artifactId>thorntail-maven-plugin</artifactId>
      <version>${version.wildfly-swarm}</version>
      <configuration>
        <mainClass>org.wildfly.swarm.examples.messaging.clustering.Main</mainClass>
      </configuration>
      <executions>
        <execution>
          <goals>
            <goal>package</goal>
          </goals>
        </execution>
      </executions>
    </plugin>

To define the needed parts of Thorntail, some dependencies are added

    <dependency>
        <groupid>io.thorntail</groupId>
        <artifactId>jaxrs</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>
    <dependency>
        <groupid>io.thorntail</groupId>
        <artifactId>messaging</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>
    <dependency>
        <groupid>io.thorntail</groupId>
        <artifactId>msc</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>

## Project `main()`

This project supplies a `main()` in order to configure the messaging
subsystem and deploy all the pieces of the application.


After the container is instantiated, the Messaging fraction is
configured and installed, enabling the in-vm connector and setting
up a topic and a queue.  

The container is started.

A JAX-RS deployment based on a project class is deployed, as is the
MSC service-activator, which activates a service to consume messages.

* mvn package
* start a first server:

    java -Djboss.messaging.cluster.password=mypassword \
       -Djava.net.preferIPv4Stack=true \
       -Dswarm.bind.address=127.0.0.1 \
       -jar target/example-messaging-clustering-swarm.jar

* start a second server with:

    java -Djboss.messaging.cluster.password=mypassword \
        -Djava.net.preferIPv4Stack=true \
        -Dswarm.bind.address=127.0.0.1 \
        -Dswarm.port.offset=100 \
        -jar target/example-messaging-clustering-swarm.jar

The `jboss.messaging.cluster.password` property is required to let only authenticated ActiveMQ nodes join the cluster.
The second server is started with `swarm.port.offset=100` so that it opens its ports with an offset of 100 added to its configuration, i.e. its HTTP port is at 8180 (8080 + 100).
The two servers will form a cluster and ActiveMQ will create a cluster connection joining the servers. IN both server logs, you will see INFO log such as:

    11:49:07,243 INFO  [org.apache.activemq.artemis.core.server] (Thread-4 (ActiveMQ-server-org.apache.activemq.artemis.core.server.impl.ActiveMQServerImpl$2@1f61df9a-467428887)) AMQ221027: Bridge ClusterConnectionBridge@3c52455d ... is connected

## Use

Each server defines a JAX-RS service that sends a JMS message to the JMS Topic `my-topic` on its own server and a MSC service that consumes JMS messages from this same topic.

Since the servers are forming a cluster, the JMS Topic is clustered and both consumers on the servers will receive the message send to other servers.

    http://localhost:8080/
    http://localhost:8180/

On the console the MSC service will print the message it received over JMS

    17:50:52,359 INFO  [stdout] (Thread-55 (ActiveMQ-client-global-threads-800929546)) received: Hello! from http://localhost:8080/
    17:50:53,275 INFO  [stdout] (Thread-61 (ActiveMQ-client-global-threads-800929546)) received: Hello! from http://localhost:8180/
