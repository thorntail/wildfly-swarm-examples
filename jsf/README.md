# JSF .war Example

This example takes a normal JSF build, and wraps it into
a `-swarm` runnable jar.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/SWARM

## Project `pom.xml`

This project is a traditional JAX-RS project, with maven packaging
of `war` in the `pom.xml`

    <packaging>war</packaging>

The project adds a `<plugin>` to configure `wildfly-swarm-plugin` to
create the runnable `.jar`.

``` xml
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
```

To define the needed parts of WildFly Swarm, a dependency is added

``` xml
<dependency>
  <groupId>org.wildfly.swarm</groupId>
  <artifactId>jsf</artifactId>
  <version>${version.wildfly-swarm}</version>
</dependency>
<dependency>
  <groupId>org.wildfly.swarm</groupId>
  <artifactId>cdi</artifactId>
  <version>${version.wildfly-swarm}</version>
</dependency>
```

These dependency provides the JSF and CDI APIs to your application, so the
project does *not* need to specify those.

## Run

* mvn package && java -jar ./target/example-jsf-war-swarm.jar
* mvn wildfly-swarm:run
* From your IDE, run class `org.wildfly.swarm.Swarm`

## Use

Since WildFly Swarm apps tend to support one deployment per executable, it
automatically adds a `jboss-web.xml` to the deployment if it doesn't already
exist.  This is used to bind the deployment to the root of the web-server,
instead of using the `.war`'s own name as the application context.

``` sh
$ curl localhost:8080/index.xhtml
<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"><head id="j_idt2">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>WildFly Swarm Facelet</title></head><body>

    <div id="top"><h1>WildFly Swarm Facelet</h1>
    </div>

    <div id="content">Hello from JSF
    </div>

    <div id="bottom">Powered by WildFly Swarm
    </div></body>

</html>%
```

or access http://localhost:8080/ in your browser.
