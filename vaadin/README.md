# Vaadin Example

This example takes a simple Vaadin application with CDI service, and wraps it into
a `-swarm` runnable jar.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/THORN

## Project `pom.xml`

This project is a traditional web app project, with maven packaging
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

We let the plugin determine which dependencies we need, so we don't add any ourselves.

To add Vaadin (and Vaadin CDI) support there are also these dependencies:

    <dependency>
        <groupId>com.vaadin</groupId>
        <artifactId>vaadin-client-compiled</artifactId>
        <version>7.5.3</version>
    </dependency>
    <dependency>
        <groupId>com.vaadin</groupId>
        <artifactId>vaadin-themes</artifactId>
        <version>7.5.3</version>
    </dependency>
    <dependency>
      <groupId>com.vaadin</groupId>
      <artifactId>vaadin-cdi</artifactId>
      <version>1.0.3</version>
    </dependency>

Additional application dependencies can be
specified and will be included in the normal `.war` construction and available
within the Thorntail application `.jar`.

## Run

You can run it many ways:

* mvn package && java -jar target/example-vaadin-swarm.jar
* mvn thorntail:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

Since Thorntail apps tend to support one deployment per executable, it
automatically adds a `jboss-web.xml` to the deployment if it doesn't already
exist.  This is used to bind the deployment to the root of the web-server,
instead of using the `.war`'s own name as the application context.

    http://localhost:8080/
