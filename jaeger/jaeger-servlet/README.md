# Jaeger simple example

This example is an extension of the OpenTracing Servlet example, but consuming
the Jaeger fraction instead. Refer to the README on that example to understand
the basics of how it works.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/THORN

## Project `pom.xml`

This project defines a dependency on the `org.wildfly.swarm:jaeger`
fraction, which brings the Jaeger and OpenTracing APIs with it.

The Servlet framework integration is part of the OpenTracing fraction and 
the filter is automatically registered. Any other framework integration 
(JAX-RS, CDI, ...) has to be provided by your application, or via other fractions.

## Running it

Like the Servlet example, you can run it many ways.

The following is what would be required to do in order to see spans generated
by this example on Jaeger.

Make sure to at least export the environment variable `JAEGER_SERVICE_NAME`, 
otherwise Jaeger will complain. A good set for development and testing purposes
is the following:

```
export JAEGER_SERVICE_NAME=swarm-jaeger-servlet-example
export JAEGER_REPORTER_LOG_SPANS=true 
export JAEGER_SAMPLER_TYPE=const
export JAEGER_SAMPLER_PARAM=1 
```

Alternatively, you can use the following Thorntail properties to configure 
the Tracer. Note that these can also be changed at runtime by your application,
by using the `org.wildfly.swarm.jaeger.JaegerFraction` or via all regular Wildfly
Swarm procedures:

```
swarm.jaeger.service-name=swarm-jaeger-servlet-example
swarm.jaeger.reporter-log-spans=true
swarm.jaeger.sampler-type=const
swarm.jaeger.sampler-parameter=1
```

Refer to the Jaeger documentation for further options.

Jaeger can be started via Docker as follows:

```
docker run \
    --rm \
    -p5775:5775/udp \
    -p6831:6831/udp \
    -p6832:6832/udp \
    -p5778:5778 \
    -p16686:16686 \
    -p14268:14268 \
    --name=jaeger \
    jaegertracing/all-in-one:latest
```

Jaeger will be available at [http://localhost:16686](http://localhost:16686)

You are now ready to run the example and have span data reported to Jaeger:

* mvn package && java -jar ./target/example-jaeger-servlet-swarm.jar
* mvn thorntail:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

You may now make HTTP calls to the application, like:

```
curl http://localhost:8080/hello
```

On Jaeger, the Trace should look like this:

![Trace example](example.png)
