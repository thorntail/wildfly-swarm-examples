# Keycloak Example

Simple JAX-RS application with Keycloak example.

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/SWARM

This example shows how the command-line and browser clients can access a JAX-RS service
secured by Keycloak.

## Start Keycloak

### Keycloak Swarm Server

Download the latest Swarm Keycloak standalone server jar, for example, 2018.4.1 version:

``` sh
wget http://repo1.maven.org/maven2/org/wildfly/swarm/servers/keycloak/2018.4.1/keycloak-2018.4.1-swarm.jar .
```
and start it:

``` sh
THIS_EXAMPLE=/path/to/this/example
java -Dswarm.http.port=8180 \
     -Dkeycloak.migration.action=import \
     -Dkeycloak.migration.provider=dir \
     -Dkeycloak.migration.file=${THIS_EXAMPLE}/realm \
     -jar keycloak-2018.4.1-swarm.jar
```


### Local installed

``` sh
THIS_EXAMPLE=/path/to/this/example
cd $KEYCLOAK_HOME
bin/standalone.sh \
  -Djboss.socket.binding.port-offset=100 \
  -Dkeycloak.migration.action=import \
  -Dkeycloak.migration.provider=dir \
  -Dkeycloak.migration.file=${THIS_EXAMPLE}/realm
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
  -Dkeycloak.migration.file=/tmp/realm
```

## Build Example

``` sh
mvn clean package
```

## Access a secured resource from shell

### Start the example server

``` sh
THIS_EXAMPLE=/path/to/this/example
java -Dswarm.keycloak.json.path=$THIS_EXAMPLE/src/main/resources/cmd-client-keycloak.json \
  -jar target/example-keycloak-swarm.jar
```

### Access the secured resource

``` sh
curl localhost:8080/app/secured -v
```

You'll get the response with `401 Unauthorized`. Let's get a Token to access it.

### Obtain Token from Keycloak Server

``` sh
USER=user1
PASS=password1
RESULT=`curl -s --data "grant_type=password&client_id=wildfly-swarm-cmd-client-example&username=${USER}&password=${PASS}" http://localhost:8180/auth/realms/wildfly-swarm-cmd-client/protocol/openid-connect/token`
TOKEN=`echo $RESULT | sed 's/.*access_token":"//g' | sed 's/".*//g'`
```

### Access the secured resource with the Token

``` sh
curl -H "Authorization: bearer $TOKEN" localhost:8080/app/secured
```

You'll get the response which contains `Hi user1, this is Secured Resource`.

## Access a secured resource from browser

### Start the example server

Stop the example server if it is already running and start it with:

``` sh
THIS_EXAMPLE=/path/to/this/example
java -Dswarm.keycloak.json.path=$THIS_EXAMPLE/src/main/resources/browser-client-keycloak.json \
  -jar target/example-keycloak-swarm.jar
```

### Access the public client page

Navigate the browser to "http://localhost:8080/" 

### Access the secured resource

Click on "Access Secured Resource"
You'll get `Unauthorized` displayed.

### Login

Click on "Login" and use the user1/password1 credentials to login into Keycloak.

### Access the secured resource

Click on "Access Secured Resource" again.

You'll get the response which contains `Hi user1, this is Secured Resource`.

