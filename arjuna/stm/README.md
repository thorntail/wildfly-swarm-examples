# JAX-RS & Arjuna Software Transactional Memory

This example uses a JAX-RS resource and AtomicAction & friends.

Note, these are not Java EE compliant APIs and classes we're going to use. But that's the point!

## Project `pom.xml`

The project is a normal maven project with `war` packaging, not `jar`.

    <packaging>war</packaging>

The project adds a `<plugin>` to configure `thorntail-maven-plugin` to
create the `.war`.

    <plugin>
      <groupid>io.thorntail</groupId>
      <artifactId>thorntail-maven-plugin</artifactId>
      <version>${version.wildfly-swarm}</version>
      <executions>
        <execution>
          <phase>package</phase>
          <goals>
            <goal>create</goal>
          </goals>
        </execution>
      </executions>
    </plugin>

Additionally, the usual `maven-war-plugin` is provided.

    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <configuration>
          <failOnMissingWebXml>false</failOnMissingWebXml>
        </configuration>
    </plugin>

To define the needed parts of Thorntail, a dependency is added

    <dependency>
        <groupid>io.thorntail</groupId>
        <artifactId>jaxrs</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>
    <dependency>
        <groupid>io.thorntail</groupId>
        <artifactId>transactions</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>

## Build

    mvn package

## Run

   mvn thorntail:run

## Use

    First try ...

    http://localhost:8080/stm

    In the browser you should see something like ...

Object name: 0:ffffc0a80102:-48ffece4:5a3fb241:d
Transactional object value initially 10
Transactional object value incremented to 11
Transactional object value incremented to 12
Transactional object value incremented to 13
Transactional object value incremented to 14
Transactional object value incremented to 15
Transactional object value incremented to 16
Transactional object value incremented to 17
Transactional object value incremented to 18
Transactional object value incremented to 19
Transactional object value incremented to 20

What's happening is that we've created a recoverable STM-managed object (an integer state) within Thorntail and given it an initial value
of 10; then we increment it by another 10, printing out each time we do.

If you repeat the operation you will see a new instance created and the end state will always be 20.

Now try ...

http://localhost:8080/stmPersistent

The first time you do will look similar to the above:

Object name: 0:ffffc0a80102:-48ffece4:5a3fb241:5d
Transactional object value initially 20
Transactional value incremented to 21
Transactional value incremented to 22
Transactional value incremented to 23
Transactional value incremented to 24
Transactional value incremented to 25
Transactional value incremented to 26
Transactional value incremented to 27
Transactional value incremented to 28
Transactional value incremented to 29
Transactional value incremented to 30

But give it a try again ...

Object name: 0:ffffc0a80102:-48ffece4:5a3fb241:5d
Transactional object value initially 30
Transactional value incremented to 31
Transactional value incremented to 32
Transactional value incremented to 33
Transactional value incremented to 34
Transactional value incremented to 35
Transactional value incremented to 36
Transactional value incremented to 37
Transactional value incremented to 38
Transactional value incremented to 39
Transactional value incremented to 40

Now you can see that each time the invocation happens we re-use the same object and the integer value just keeps increasing.