# Logstash Example

This example sends log messages to Logstash.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/THORN

## Start Logstash

### Local installed

``` sh
cd $LOGSTASH_HOME
bin/logstash -f /this/project/pipeline/logstash-wildfly.conf
```

### Docker

``` sh
docker run --rm -it \
  -v /this/project/pipeline:/usr/share/logstash/pipeline \
  -p 9300:9300 \
  docker.elastic.co/logstash/logstash:5.1.1
```

## Build & Run Example

``` sh
mvn clean package
java -jar target/examples-logstash-swarm.jar
```

## Example Requests

If you access the app [APIs](#apis), you can see the following log in Logstash console.

``` sh
curl localhost:8080/info
```

```
{
     "loggerClassName" => "org.jboss.logging.Logger",
               "level" => "INFO",
             "message" => "This is INFO message",
                 "ndc" => "",
                 "mdc" => {},
          "threadName" => "default task-2",
                "tags" => [],
            "threadId" => 162,
            "sequence" => 27,
          "@timestamp" => 2017-01-06T13:58:55.380Z,
                "port" => 35318,
            "@version" => 1,
                "host" => "127.0.0.1",
    "wildflySwarmNode" => "your-host-name",
          "loggerName" => "org.wildfly.examples.swarm.logstash.MyResource"
}
```

### APIs

* /debug
* /info
* /warn
* /error
* /exception