#JAX-RS and Apache Deltaspike Data Module

This example uses Apache Deltaspike Data Module with JAXRS.

## Project `pom.xml`

This project is intented for use with the following approaches:

1. `org.wildfly.swarm.Swarm` class
2. Thorntail Plugin

In order to achieve these objectives the packaging type must be:

     <packaging>war</packaging>
     
Additionally you must include wildfly-swarm maven plugin in your `pom.xml`

            <plugin>
                <groupid>io.thorntail</groupId>
                <artifactId>thorntail-maven-plugin</artifactId>
                <version>${version.wildfly.swarm}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>package</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>start</id>
                    </execution>
                    <execution>
                        <id>stop</id>
                    </execution>
                </executions>
            </plugin>

## Fractions used
 
- jaxrs
- cdi
- jpa

##Testing

## Startup

### `org.wildfly.swarm.Swarm` class

From your IDE run the `org.wildfly.swarm.Swarm` class

### Maven plugin
    
From your shell/prompt console just enter the following command
    
    mvn thorntail:run
   

## Testing with curl

### GET all 

    curl http://localhost:8080/api/persons/ -i;
    
### GET by name like

    curl http://localhost:8080/api/persons?name=JOHN -i;
    
### GET by document ID equals and name like

    curl http://localhost:8080/api/persons?document-id=320b8e6bef45211f0f57b618925f4193\&name=J -i;
    
### GET by document ID equals

    curl http://localhost:8080/api/persons?document-id=320b8e6bef45211f0f57b618925f4193 -i;
    
### PUT 

    curl http://localhost:8080/api/persons/1 -X PUT -H "Content-type: application/json" -d '{"id":1,"name":"TEST DONE","documentId":"66677fb9980dcc0f996c91e08ef6d6de"}';
    
### POST

    curl http://localhost:8080/api/persons/ -X POST -H "Content-type: application/json" -d '{"name":"MARIO DO ARMARIO","documentId":"a607feec176eb4b4fc32d7ca69f8e343"}';
