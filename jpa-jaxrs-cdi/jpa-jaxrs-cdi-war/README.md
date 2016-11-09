# JPA, JAX-RS and CDI .war Example

This example takes a normal JPA, CDI and JAX-RS build, and wraps it into
a `-swarm` runnable jar.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/SWARM

## Project `pom.xml`

This project is a normal maven project with `war` packaging.

    <packaging>war</packaging>

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

We let the plugin define the parts of WildFly Swarm that we need based on the
APIs our code uses, so we don't need to define any dependencies explicitly.

## Run

You can run it many ways:

* mvn package && java -jar ./target/example-jpa-jaxrs-cdi-war-swarm.jar
* mvn wildfly-swarm:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

    http://localhost:8080/
