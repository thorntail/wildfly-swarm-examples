# WildFly kitchensink-html5-mobile Example

This example takes the [kitchensick-html5-mobile](https://github.com/wildfly/quickstart/tree/master/kitchensink-html5-mobile)
quickstart from WildFly, and wraps it into a `-swarm` runnable jar.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/THORN

## Project `pom.xml`

This project is a traditional CDI, JPA, JAX-RS, EJB project, with maven packaging
of `war` in the `pom.xml`

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

To define the needed parts of Thorntail, the following dependencies are added

    <dependency>
      <groupid>io.thorntail</groupId>
      <artifactId>jaxrs-weld</artifactId>
      <version>${version.wildfly-swarm}</version>
    </dependency>
    <dependency>
      <groupid>io.thorntail</groupId>
      <artifactId>jpa</artifactId>
      <version>${version.wildfly-swarm}</version>
    </dependency>
    <dependency>
      <groupid>io.thorntail</groupId>
      <artifactId>ejb</artifactId>
      <version>${version.wildfly-swarm}</version>
    </dependency>
    <dependency>
      <groupid>io.thorntail</groupId>
      <artifactId>hibernate-validator</artifactId>
      <version>${version.wildfly-swarm}</version>
    </dependency>

This dependency provides the EJB, JPA, JAX-RS, and CDI APIs to your application, so the
project does *not* need to specify those.

Additional application dependencies can be
specified and will be included in the normal `.war` construction and available
within the Thorntail application `.jar`.

## Run

You can run it many ways:

* mvn package && java -jar ./target/wildfly-swarm-example-kitchensink-html5-mobile-swarm.jar
* mvn thorntail:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

Since Thorntail apps tend to support one deployment per executable, it
automatically adds a `jboss-web.xml` to the deployment if it doesn't already
exist.  This is used to bind the deployment to the root of the web-server,
instead of using the `.war`'s own name as the application context.

    http://localhost:8080/
