# JPA and Servlet .war Example

This example takes a normal JPA and Servlet build, and wraps it into
a `-swarm` runnable jar.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/THORN

## Project `pom.xml`

This project is a traditional JPA project with `war` packaging.

    <packaging>war</packaging>

The project adds a `<plugin>` to configure `thorntail-maven-plugin` to
create the runnable `.jar`.

    <plugin>
      <groupid>io.thorntail</groupId>
      <artifactId>thorntail-maven-plugin</artifactId>
      <version>${version.wildfly-swarm}</version>
      <executions>
        <execution>
          <goals>
            <goal>package</goal>
          </goals>
        </execution>
      </executions>
    </plugin>

To define the needed parts of Thorntail, a few dependencies are added

    <dependency>
        <groupid>io.thorntail</groupId>
        <artifactId>jpa-eclipselink</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>
    <dependency>
        <groupid>io.thorntail</groupId>
        <artifactId>undertow</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>

The `jpa-eclipselink` dependency provides the JPA APIs with EclipseLink as JPA provider and `undertow` provides the Servlet
APIs.

## Run

You can run it many ways:

* mvn package && java -jar ./target/example-jpa-eclipselink-swarm.jar
* mvn thorntail:run
* From your IDE, run class `org.wildfly.swarm.Swarm`

## Use

    http://localhost:8080/
