# JAX-WS .war Example

This example takes a normal JAX-WS build, and wraps it into
a `-swarm` runnable jar.

It is originally from [wildfly-quickstart helloworld-ws](https://github.com/wildfly/quickstart/tree/10.x/helloworld-ws).

> Please raise any issues found with this example in this repo:
> https://github.com/wildfly-swarm/wildfly-swarm-examples
>
> Issues related to WildFly Swarm core should be raised in the main repo:
> https://github.com/wildfly-swarm/wildfly-swarm/issues

## Project `pom.xml`

This project is a traditional JAX-RS project, with maven packaging
of `war` in the `pom.xml`

``` xml
<packaging>war</packaging>
```

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
    <artifactId>wildfly-swarm-webservices</artifactId>
    <version>${version.wildfly-swarm}</version>
</dependency>
```

This dependency provides the JAX-WS APIs to your application, so the
project does *not* need to specify those.

## Run

* mvn package && java -jar ./target/wildfly-swarm-example-jaxws-swarm.jar
* mvn wildfly-swarm:run
* From your IDE, run class `org.wildfly.swarm.Swarm`

## Use

Since WildFly Swarm apps tend to support one deployment per executable, it
automatically adds a `jboss-web.xml` to the deployment if it doesn't already
exist.  This is used to bind the deployment to the root of the web-server,
instead of using the `.war`'s own name as the application context.

```
http://localhost:8080/
```

The above resource displays the `wsdl` link.
 
## Run the Client Tests using Arquillian

This example has a test case to access the webservice from a client with Arquillian.

``` sh
$ mvn clean test
...
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running org.wildfly.swarm.examples.jaxws.ClientArqTest
...
2015-11-13 01:51:10,702 INFO  [org.jboss.ws.cxf.metadata] (MSC service thread 1-4) JBWS024061: Adding service endpoint metadata: id=org.wildfly.swarm.examples.jaxws.HelloWorldServiceImpl
 address=http://localhost:8080/HelloWorldService
 implementor=org.wildfly.swarm.examples.jaxws.HelloWorldServiceImpl
 serviceName={http://wildfly-swarm.io/HelloWorld}HelloWorldService
 portName={http://wildfly-swarm.io/HelloWorld}HelloWorld
 annotationWsdlLocation=null
 wsdlLocationOverride=null
 mtomEnabled=false
...
[Client] Requesting the WebService to say Hello.
[WebService] Hello World!
[Client] Requesting the WebService to say Hello to John.
[WebService] Hello John!
[Client] Requesting the WebService to say Hello to John, Mary and Mark.
[WebService] Hello John, Mary & Mark!
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 44.405 sec

Results :

Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
```