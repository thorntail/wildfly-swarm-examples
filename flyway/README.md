# Flyway Example

This example contains a simple `.war` build with some SQL scripts that
will be executed when the application starts. By default it uses the primary
configured DataSource.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/THORN

## Project `pom.xml`

This project is a simple `.war` project

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

To define the needed parts of Thorntail, a dependency is added

    <dependency>
        <groupid>io.thorntail</groupId>
        <artifactId>flyway</artifactId>
        <version>${version.wildfly-swarm}</version>
    </dependency>

## Run

* `mvn package && java -jar ./target/example-flyway-swarm.jar`
* Or `mvn thorntail:run`

## Use

Watch the console the following messages.

    2016-10-24 13:38:50,547 INFO  [org.flywaydb.core.internal.util.VersionPrinter] (ServerService Thread Pool -- 11) Flyway 4.0.3 by Boxfuse
    2016-10-24 13:38:50,742 INFO  [org.flywaydb.core.internal.dbsupport.DbSupportFactory] (ServerService Thread Pool -- 11) Database: jdbc:h2:mem:test (H2 1.4)
    2016-10-24 13:38:50,817 INFO  [org.flywaydb.core.internal.command.DbValidate] (ServerService Thread Pool -- 11) Successfully validated 2 migrations (execution time 00:00.007s)
    2016-10-24 13:38:50,831 INFO  [org.flywaydb.core.internal.metadatatable.MetaDataTableImpl] (ServerService Thread Pool -- 11) Creating Metadata table: "PUBLIC"."schema_version"
    2016-10-24 13:38:50,852 INFO  [org.flywaydb.core.internal.command.DbMigrate] (ServerService Thread Pool -- 11) Current version of schema "PUBLIC": << Empty Schema >>
    2016-10-24 13:38:50,852 INFO  [org.flywaydb.core.internal.command.DbMigrate] (ServerService Thread Pool -- 11) Migrating schema "PUBLIC" to version 1 - Create person table
    2016-10-24 13:38:50,870 INFO  [org.flywaydb.core.internal.command.DbMigrate] (ServerService Thread Pool -- 11) Migrating schema "PUBLIC" to version 2 - Add people
    2016-10-24 13:38:50,880 INFO  [org.flywaydb.core.internal.command.DbMigrate] (ServerService Thread Pool -- 11) Successfully applied 2 migrations to schema "PUBLIC" (execution time 00:00.050s).

Access http://localhost:8080 and you should see the following:

    Data from PERSON table
    1 - Axel
    2 - Mr. Foo
    3 - Ms. Bar
    
