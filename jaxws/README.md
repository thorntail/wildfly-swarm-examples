# JAX-WS .war Example

This example takes a normal JAX-WS build, and wraps it into
a `-swarm` runnable jar.

It is originally from [wildfly-quickstart helloworld-ws](https://github.com/wildfly/quickstart/tree/10.x/helloworld-ws).

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/THORN

## Project `pom.xml`

This project is a traditional JAX-WS project, with maven packaging
of `war` in the `pom.xml`

``` xml
<packaging>war</packaging>
```

The project adds a `<plugin>` to configure `thorntail-maven-plugin` to
create the runnable `.jar`.

``` xml
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
```

To define the needed parts of Thorntail, a dependency is added

``` xml
<dependency>
  <groupid>io.thorntail</groupId>
  <artifactId>webservices</artifactId>
  <version>${version.wildfly-swarm}</version>
</dependency>
```

This dependency provides the JAX-WS APIs to your application, so the
project does *not* need to specify those.

## Run

* mvn package && java -jar ./target/example-jaxws-swarm.jar
* mvn thorntail:run
* From your IDE, run class `org.wildfly.swarm.Swarm`

## Use

Since Thorntail apps tend to support one deployment per executable, it
automatically adds a `jboss-web.xml` to the deployment if it doesn't already
exist.  This is used to bind the deployment to the root of the web-server,
instead of using the `.war`'s own name as the application context.

```
http://localhost:8080/
```

The above resource displays the `wsdl` link.
