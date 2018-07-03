# JAX-RS & Arjuna Transactions

This example uses a JAX-RS resource and AtomicAction & friends.

Note, these are not Java EE compliant APIs and classes we're going to use. But that's the point!

## Project `pom.xml`

The project adds a `<plugin>` to configure `thorntail-maven-plugin` to
create the runnable `.jar`.

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

    java -jar ./target/example-arjuna-swarm.jar

## Use

    First try ...

    http://localhost:8080/atomicaction

    In the browser you should see something like ...

    Begin BasicAction: 0:ffffac1c8001:2fb05ec4:567ed3a5:b status: ActionStatus.RUNNING
    Committed BasicAction: 0:ffffac1c8001:2fb05ec4:567ed3a5:b status: ActionStatus.COMMITTED

    Then try ...

    http://localhost:8080/begincommit

    Should result in ...

    Transaction begun ok and committed ok

    Now try ...

    http://localhost:8080/beginrollback

    You should get something like the following in your browser ...

    Transaction begun ok and rolled back ok

    Now let's try something which isn't typically supported in Java EE applications ... Nested Transactions! Yes, you can have an arbitrarily nested transaction hierarchy and even if child transactions commit, their work will still be undone (aborted) if an enclosing parent transaction later rolls back. Likewise, the impact of aborting a child transaction does not have to impact the outcome of a parent transaction.

    So go to your browser and try ...

    http://localhost:8080/nestedChildCommit

    You should see something like ...

    Nested transaction  BasicAction: 0:ffffc0a80102:-71b7e35b:5a3e32e2:e status: ActionStatus.RUNNING started!
    Child and parent committed ok!

    Let's try to abort the child transaction whilst still committing the parent. So go to ...

    http://localhost:8080/nestedChildAbort

    And you should see ...

    Nested transaction  BasicAction: 0:ffffc0a80102:-71b7e35b:5a3e32e2:12 status: ActionStatus.RUNNING started!
    Child aborted and parent still committed ok!

    Following on from the nested transaction concept we also have what are often called nested top-level transactions. Basically a
    nested top-level transaction can be created anywhere within a transaction hierarchy and it will always be treated as an independent
    transaction such that if it is created and then committed within a nested context, the results are not rolled back if the enclosing
    transaction subsequently decides to abort.

    Give it a go by trying ...

    http://localhost:8080/nestedTopLevelCommit

    And you should see something like ...

    Nested transaction  BasicAction: 0:ffffc0a80102:-71ab9d5e:5a3e6477:10 status: ActionStatus.RUNNING started!
    Nested top-level action started BasicAction: 0:ffffc0a80102:-71ab9d5e:5a3e6477:11 status: ActionStatus.RUNNING
    Nested top-level action committed ok!
    Child nested action aborted and parent still committed ok!

    We'll leave it as an exercise to the reader to figure out what an abort of a nested top-level transaction in this case
    might generate.