# Camel CDI Example

This example uses camel-cdi for enabling CDI beans to participate in a Camel route.

## Project `pom.xml`

The project adds a `<plugin>` to configure `wildfly-swarm-plugin` to
create the runnable `.jar`.

    <plugin>
      <groupId>org.wildfly.swarm</groupId>
      <artifactId>wildfly-swarm-plugin</artifactId>
      <version>${version.wildfly-swarm}</version>
      <executions>
        <execution>
          <goals>
            <goal>package</goal>
          </goals>
        </execution>
      </executions>
    </plugin>

To define the needed parts of WildFly Swarm, the following dependencies are added

    <dependency>
        <groupId>org.wildfly.swarm</groupId>
        <artifactId>camel-full</artifactId>
    </dependency>

This dependency provides Camel and CDI APIs to your application, so the
project does *not* need to specify those.

## Run

You can run it many ways:

* mvn package && java -jar ./target/example-camel-cdi-swarm.jar
* mvn wildfly-swarm:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

Once the Swarm container has started, you can test the Camel CDI application by hitting the following HTTP endpoint.

    http://localhost:8080/hello?name=bob

You should see a response of 'Hello bob'
