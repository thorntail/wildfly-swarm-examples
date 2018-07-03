# Management Console

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
        <artifactId>management-console</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>
    <dependency>
        <groupid>io.thorntail</groupId>
        <artifactId>management</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>

## Project `main()`

This project supplies a `main()` in order to configure the management
console and deploy all the pieces of the application.

After the container is instantiated, the management-fraction is 
configured with a management user `bob` and password `tacos!` and 
enabled on port 9090.

Then as this project depends on the management-console fraction, the 
fraction is configured and installed, enabling a new `/console` context 
root. 

The container is started.

    mvn package

Then start the server

    java -jar target/example-management-console-swarm.jar
       

## Use

Open the following URL and you should see the management console UI.

    http://localhost:8080/console

### Adding management hosts

Click on the Add button and enter the following information: 

    Name: Localhost
    Scheme: http
    Hostname: 127.0.0.1
    Port: 9990

Press the Ping button and present the following authentication info:

    User: bob 
    Password: tacos!
 
If it's correctly configured it should present a message saying 
*The management interface is running.*

Click Add and then Connect. You should see the management console UI.
