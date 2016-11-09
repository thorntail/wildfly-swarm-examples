# JAX-RS and CDI .war Example

This example takes a normal JAX-RS and CDI build, and wraps it into
a `-swarm` runnable jar.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/SWARM

## Project `pom.xml`

This project is a traditional JAX-RS and CDI project, with maven packaging
of `war` in the `pom.xml`

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

To define the needed parts of WildFly Swarm, the following dependency is added

    <dependency>
      <groupId>org.wildfly.swarm</groupId>
      <artifactId>jaxrs</artifactId>
      <version>${version.wildfly-swarm}</version>
    </dependency>
    <dependency>
      <groupId>org.wildfly.swarm</groupId>
      <artifactId>cdi</artifactId>
      <version>${version.wildfly-swarm}</version>
    </dependency>

These dependencies provide the JAX-RS and CDI APIs to your application, so the
project does *not* need to specify those.

Additional application dependencies (in this case `lombok`) can be
specified and will be included in the normal `.war` construction and available
within the WildFly Swarm application `.jar`.

## Run

You can run it many ways:

* mvn package && java -jar ./target/example-jaxrs-deltaspike-swarm.jar
* mvn wildfly-swarm:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

Since WildFly Swarm apps tend to support one deployment per executable, it
automatically adds a `jboss-web.xml` to the deployment if it doesn't already
exist.  This is used to bind the deployment to the root of the web-server,
instead of using the `.war`'s own name as the application context.

To access the JAX-RS Resource:

    http://localhost:8080/employees
