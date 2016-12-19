# Wildfly Swarm OpenTracing Hawkular Example

Swarm JAX-RS application instrumented with OpenTracing API and reporting data to Hawkular-APM.

Start [Hawkular-APM](https://hawkular.gitbooks.io/hawkular-apm-user-guide/content/quickstart/) server 
with `-Djboss.socket.binding.port-offset=100`.

## Build & Run
```
mvn clean package
java -jar target/examples-opentracing-hawkular-swarm.jar -Dswarm.http.port=3000
```

## Example Requests
```
curl -ivX GET 'http://localhost:3000/hello'
curl -ivX GET 'http://localhost:3000/localspan'
```

Reported data should appear in Hawkular-APM UI [localhost:8180](http://localhost:8180).

