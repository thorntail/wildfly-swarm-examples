# Microprofile OpenTracing Example

## Run

Define environmental properties to configure Jeager tracer which will be used inside 
`microprofile-opentracing` fraction:
```bash
export JAEGER_SERVICE_NAME=thorntail
export JAEGER_REPORTER_LOG_SPANS=true 
export JAEGER_SAMPLER_TYPE=const
export JAEGER_SAMPLER_PARAM=1 
```

Run the app:
```bash
mvn thorntail:run
```

## Run Jaeger 

Jaeger can be started via Docker as follows:
```bash
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

## Example requests
```bash
curl localhost:8080
curl localhost:8080/chaining
curl localhost:8080/health
curl localhost:8080/bean
```
