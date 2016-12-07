# JAX-RS & Arjuna Software Transactional Memory

This example uses a JAX-RS resource and AtomicAction & friends.

Note, these are not Java EE compliant APIs and classes we're going to use. But that's the point!

## Project `pom.xml`

The project is a normal maven project with `war` packaging, not `jar`.

    <packaging>war</packaging>

The project adds a `<plugin>` to configure `wildfly-swarm-plugin` to
create the `.war`.

    <plugin>
      <groupId>org.wildfly.swarm</groupId>
      <artifactId>wildfly-swarm-plugin</artifactId>
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

To define the needed parts of WildFly Swarm, a dependency is added

    <dependency>
        <groupId>org.wildfly.swarm</groupId>
        <artifactId>jaxrs</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>
    <dependency>
        <groupId>org.wildfly.swarm</groupId>
        <artifactId>transactions</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>
    <dependency>
        <groupId>org.wildfly.swarm</groupId>
        <artifactId>msc</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>

## Build

    mvn package

## Run

   mvn wildfly-swarm:run

## Use

    First try ...

    http://localhost:8080/stm

    In the browser you should see something like ...

Object name: 0:ffffac1c8001:a3d6627:567f024a:b

Transaction value hello11

Transaction value hello12

Transaction value hello13

Transaction value hello14

Transaction value hello15

Transaction value hello16

Transaction value hello17

Transaction value hello18

Transaction value hello19

Transaction value hello20

Note that depending upon which version of Narayana STM you're using, you may see some warnings like the following in the console ...

2015-12-26 21:00:32,498 WARN  [com.arjuna.ats.arjuna] (default task-1) ARJUNA012281: ShadowingStore::read_state() - no type name given for object state 0:ffffac1c8001:-62da914f:567effe8:b

You can safely ignore them as they're due to a known issue in some versions of Narayana ...

https://issues.jboss.org/browse/JBTM-2592