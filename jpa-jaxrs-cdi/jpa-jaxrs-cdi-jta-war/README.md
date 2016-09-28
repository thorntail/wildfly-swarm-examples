# JPA, JAX-RS, CDI and JTA with .war Example

This example takes a normal JPA, JTA (transaction), CDI, JAX-RS build, and wraps it into
a `-swarm` runnable jar.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/SWARM

## Project `pom.xml`

This project is a normal maven project with `war` packaging.

    <packaging>war</packaging>

The project adds a `<plugin>` to configure `wildfly-swarm-plugin` to
create the runnable `.jar`.

      <plugin>
        <groupId>org.wildfly.swarm</groupId>
        <artifactId>wildfly-swarm-plugin</artifactId>
        <executions>
          <execution>
            <id>package</id>
          </execution>
          <execution>
            <id>start</id>
          </execution>
          <execution>
            <id>stop</id>
          </execution>
        </executions>
      </plugin>
      
To define the needed parts of WildFly Swarm, a few dependencies are added

    <dependency>
      <groupId>org.jboss.spec.javax.ws.rs</groupId>
      <artifactId>jboss-jaxrs-api_2.0_spec</artifactId>
      <version>1.0.0.Final</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>org.hibernate.javax.persistence</groupId>
      <artifactId>hibernate-jpa-2.1-api</artifactId>
      <version>1.0.0.Final</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>javax.enterprise</groupId>
      <artifactId>cdi-api</artifactId>
      <version>1.2</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>org.jboss.spec.javax.transaction</groupId>
      <artifactId>jboss-transaction-api_1.2_spec</artifactId>
      <version>1.0.1.Final</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>org.jboss.logging</groupId>
      <artifactId>jboss-logging</artifactId>
      <version>3.3.0.Final</version>
      <scope>provided</scope>
    </dependency>
        
The `org.hibernate.javax.persistence:hibernate-jpa-2.1-api` provides the JPA APIs.
The `javax.enterprise:cdi-api` provides the CDI api.
The `org.jboss.spec.javax.ws.rs:jboss-jaxrs-api_2.0_spec` provides the JAX-RS api.
The `org.jboss.spec.javax.jboss-transaction-api_1.2_spec` provides JTA API.
The `org.jboss.logging:jboss-logging` provides JBoss Logging required by observer bean; 


## Run

You can run it many ways:

* mvn package && java -jar ./target/example-jpa-jaxrs-cdi-jta-swarm.jar
* mvn wildfly-swarm:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

Request all Employees:

    http://localhost:8080/all
    
Start a transactional removal of all employee that will fail with a rollback:

    http://localhost:8080/remWithRollback

Start a transactional removal of all employee that will fail without a rollback (partial commit):

    http://localhost:8080/remWithoutRollback