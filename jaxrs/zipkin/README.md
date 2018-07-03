# Thorntail Zipkin JAX-RS Fraction Example

Swarm JAX-RS application instrumented with Zipkin.

## Start Zipkin Server
```
docker run -d -p 9411:9411 openzipkin/zipkin
```

## Build & Run Example
```
mvn clean package
java -jar target/examples-zipkin-jaxrs-swarm.jar
```

## Example Requests
```
curl -ivX GET 'http://localhost:8080/hello'
```

Reported spans should appear in zipkin UI [localhost:9411](http://localhost:9411).

