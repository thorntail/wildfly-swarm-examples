# JPA, JAX-RS, CDI and JTA with Shrinkwrap Example

This example uses JPA, JTA (transaction), CDI, JAX-RS resources deployed through a user-provided
`main()` programmatically without constructing a `.war` file during the build.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/SWARM

## Project `pom.xml`

This project is a normal maven project with `jar` packaging, not `war`.

    <packaging>jar</packaging>

The project adds a `<plugin>` to configure `wildfly-swarm-plugin` to
create the runnable `.jar`.

    <plugin>
      <groupId>org.wildfly.swarm</groupId>
      <artifactId>wildfly-swarm-plugin</artifactId>
      <configuration>
        <mainClass>org.wildfly.swarm.examples.jpajaxrscdijta.Main</mainClass>
      </configuration>
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
      <groupId>org.wildfly.swarm</groupId>
      <artifactId>jaxrs-cdi</artifactId>
    </dependency>
    <dependency>
      <groupId>org.wildfly.swarm</groupId>
      <artifactId>jpa</artifactId>
    </dependency>
    <dependency>
      <groupId>org.wildfly.swarm</groupId>
      <artifactId>transactions</artifactId>
    </dependency>
    
The `org.wildfly.swarm:jpa` dependency allows usage of ShrinkWrap APIs within the `main()`,
provides configuration classes for adding the JDBC driver and datasources to the container,
in addition to providing the JPA APIs.  The `org.wildfly.swarm:jaxrs-cdi` provides the CDI and JAX-RS
APIs. The `org.wildfly.swarm:transaction` provides JTA API.

Additionally, the JDBC driver jar you wish to deploy is specified as a dependency
within your `pom.xml`

    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>1.4.187</version>
    </dependency>

## Project `main()`

Since this project deploys CDI and JAX-RS resources without a `.war` being constructed, it
provides its own `main()` method (specified above via the `wildfly-swarm-plugin`) to
configure the container and deploy the resources programmatically. Additionally,
it deploys the JDBC driver jar using a simplified Maven GAV (no version is required)
and deploys a datasource.

    package org.wildfly.swarm.examples.jpajaxrscdijta;
    
    import org.jboss.shrinkwrap.api.ShrinkWrap;
    import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
    import org.wildfly.swarm.Swarm;
    import org.wildfly.swarm.datasources.DatasourcesFraction;
    import org.wildfly.swarm.jaxrs.JAXRSArchive;
    import org.wildfly.swarm.jpa.JPAFraction;
    
    public class Main {
        public static void main(String[] args) throws Exception {
            Swarm swarm = new Swarm();
    
            swarm.fraction(new DatasourcesFraction()
                                   .dataSource("MyDS", (ds) -> {
                                       ds.driverName("h2");
                                       ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
                                       ds.userName("sa");
                                       ds.password("sa");
                                   })
            );
    
            swarm.fraction(new JPAFraction()
                                   .defaultDatasource("jboss/datasources/MyDS")
            );
    
            swarm.start();
    
            JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
            deployment.addClasses(Employee.class, EmployeeService.class, EmployeeObservers.class);
            deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml", Main.class.getClassLoader()), "classes/META-INF/persistence.xml");
            deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/load.sql", Main.class.getClassLoader()), "classes/META-INF/load.sql");
            deployment.addResource(EmployeeResource.class);
            deployment.addAllDependencies();
            swarm.deploy(deployment);
        }
    }

This method constructs a new default Container, which automatically
initializes all fractions (or subsystems) that are available.

The datasources fraction has no particular default configuration, so by providing a
specific configuration we enable a driver and a datasource.

JNDI names are bound automatically.

We prevent the JPA fraction from automatically configuring a default driver and datasource
as we want to define that ourselves.

The empty container is started.

A `JAXRSDeployment` is constructed, and the JAX-RS resource class is
added to it, along with the JPA Entity class, CDI bean classes, persistence.xml and load.sql.

By default, if no JAX-RS `Application` is provided a default is added
to the deployment specifying an `@ApplicationPath("/")` to bind the
deployment to the root URL.

## Run

You can run it many ways:

* mvn package && java -jar ./target/example-jpa-jaxrs-cdi-jta-shrinkwrap-swarm.jar
* mvn wildfly-swarm:run
* In your IDE run the `org.wildfly.swarm.examples.jpajaxrscdijta.Main` class

## Use

Request all Employees:

    http://localhost:8080/all
    
Start a transactional removal of all employee that will fail with a rollback:

    http://localhost:8080/remWithRollback

Start a transactional removal of all employee that will fail without a rollback (partial commit):

    http://localhost:8080/remWithoutRollback