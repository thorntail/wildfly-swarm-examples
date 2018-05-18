# Keycloak Multitenancy Example

This example shows how the application and health endpoints requiring different Keycloak adapter configurations can be secured.

## Start Keycloak

### Keycloak Swarm Server

Download the latest Swarm Keycloak standalone server jar, for example, 2018.5.0 version:

``` sh
wget http://repo1.maven.org/maven2/org/wildfly/swarm/servers/keycloak/2018.5.0/keycloak-2018.5.0-swarm.jar .
```
and start it:

``` sh
THIS_EXAMPLE=/path/to/this/example
java -Dswarm.http.port=8180 \
     -Dkeycloak.migration.action=import \
     -Dkeycloak.migration.provider=dir \
     -Dkeycloak.migration.dir=${THIS_EXAMPLE}/realm \
     -jar keycloak-2018.5.0-swarm.jar
```


### Local installed

``` sh
THIS_EXAMPLE=/path/to/this/example
cd $KEYCLOAK_HOME
bin/standalone.sh \
  -Djboss.socket.binding.port-offset=100 \
  -Dkeycloak.migration.action=import \
  -Dkeycloak.migration.provider=dir \
  -Dkeycloak.migration.dir=${THIS_EXAMPLE}/realm
```

### Docker

``` sh
docker run -it -d \
  -p 8180:8080 \
  -v `pwd`/realm:/tmp/realm \
  jboss/keycloak:3.4.0.Final \
  -b 0.0.0.0 \
  -Dkeycloak.migration.action=import \
  -Dkeycloak.migration.provider=dir \
  -Dkeycloak.migration.dir=/tmp/realm
```

## Build Example

``` sh
mvn clean package
```

## Start the example server

``` sh
java -jar target/example-keycloak-multitenancy-swarm.jar
```

### Access the secured application endpoint

``` sh
curl localhost:8080/app/secured -v
```

You'll get the response with `401 Unauthorized`. Let's get a Token to access it.

### Obtain Token from Keycloak Server

``` sh
USER=user1
PASS=password1
RESULT=`curl -s --data "grant_type=password&client_id=wildfly-swarm-app-client&username=${USER}&password=${PASS}" http://localhost:8180/auth/realms/wildfly-swarm-client/protocol/openid-connect/token`
TOKEN=`echo $RESULT | sed 's/.*access_token":"//g' | sed 's/".*//g'`
```

### Access the secured resource with the Token

``` sh
curl -H "Authorization: bearer $TOKEN" localhost:8080/app/secured
```

You'll get the response which contains `Hi user1, this is Secured Resource`.

### Access the secured health endpoints

``` sh
curl localhost:8080/app/health -v
```

You'll get the response with `401 Unauthorized`. Let's get a Token to access it.

### Obtain Token from Keycloak Server

``` sh
USER=user1
PASS=password1
RESULT=`curl -s --data "grant_type=password&client_id=wildfly-swarm-health-client&username=${USER}&password=${PASS}" http://localhost:8180/auth/realms/wildfly-swarm-client/protocol/openid-connect/token`
TOKEN=`echo $RESULT | sed 's/.*access_token":"//g' | sed 's/".*//g'`
```

### Access the secured resource with the Token

``` sh
curl -H "Authorization: bearer $TOKEN" localhost:8080/health
```

You'll get the information about the service health

