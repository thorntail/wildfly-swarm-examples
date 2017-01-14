# Keycloak Example

Simple JAX-RS application with Keycloak example.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/SWARM

This example is based on the following Keycloak blog article.

[Getting started with Keycloak - Securing a REST Service](http://blog.keycloak.org/2015/10/getting-started-with-keycloak-securing.html)

## Start Keycloak

### Local installed

``` sh
THIS_EXAMPLE=/path/to/this/example
cd $KEYCLOAK_HOME
bin/standalone.sh \
  -Djboss.socket.binding.port-offset=100 \
  -Dkeycloak.migration.action=import \
  -Dkeycloak.migration.provider=singleFile \
  -Dkeycloak.migration.file=${THIS_EXAMPLE}/realm/wildfly-swarm-keycloak-example-realm.json
```

### Docker

``` sh
docker run -it -d \
  -p 8180:8080 \
  -v `pwd`/realm:/tmp/realm \
  jboss/keycloak:2.5.0.Final \
  -b 0.0.0.0 \
  -Dkeycloak.migration.action=import \
  -Dkeycloak.migration.provider=singleFile \
  -Dkeycloak.migration.file=/tmp/realm/wildfly-swarm-keycloak-example-realm.json
```

## Build & Run Example

``` sh
mvn clean package
java -jar target/example-keycloak-swarm.jar
```

## Access a secured resource

``` sh
curl localhost:8080/secured -v
```

You'll get the response with `401 Unauthorized` . Let's get a Token to access it.

## Obtain Token from Keycloak Server

``` sh
USER=user1
PASS=password1
RESULT=`curl -s --data "grant_type=password&client_id=curl&username=${USER}&password=${PASS}" http://localhost:8180/auth/realms/wildfly-swarm-keycloak-example/protocol/openid-connect/token`
TOKEN=`echo $RESULT | sed 's/.*access_token":"//g' | sed 's/".*//g'`
```

## Access the secured resource with the Token

``` sh
curl -H "Authorization: bearer $TOKEN" localhost:8080/secured
```

You'll get the response which contains `This is Secured Resource`.