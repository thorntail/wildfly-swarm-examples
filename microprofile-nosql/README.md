# MicroProfile plus MongoDB in a .war Example

This example takes a normal [MicroProfile](http://microprofile.io) WAR, and wraps it into
a `-thorntail` runnable jar, with a little added MongoDB access for more fun!

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/THORN

## Project `pom.xml`

This project is a traditional WAR project, with maven packaging
of `war` in the `pom.xml`

    <packaging>war</packaging>

The project adds a `<plugin>` to configure `thorntail-maven-plugin` to
create the runnable `.jar`.

    <plugin>
      <groupid>io.thorntail</groupId>
      <artifactId>thorntail-maven-plugin</artifactId>
      <version>${version.thorntail}</version>
      <executions>
        <execution>
          <goals>
            <goal>package</goal>
          </goals>
        </execution>
      </executions>
    </plugin>

To define the needed parts of Thorntail, a dependency is added

    <dependency>
        <groupid>io.thorntail</groupId>
        <artifactId>microprofile</artifactId>
        <version>${version.thorntail}</version>
    </dependency>

This dependency provides the APIs that are part of the MicroProfile
to your application, so the project does *not* need to specify those.

Additional application dependencies (in this case `joda-time`) can be
specified and will be included in the normal `.war` construction and available
within the Thorntail application `.jar`.

## Start MongoDB

First start the MondoDB server, which may take some time to launch in the background (which is why we hacked a sleep of 5 seconds below).
Then, create 'devuser' account and set the password to 'tTeX%4#MpN0~sQl':

* docker run -d --name some-mongo -p 27017:27017 -e MONGO_INITDB_DATABASE=mongotestdb mongo
* sleep 5
* docker exec some-mongo /bin/sh -c "mongo mongotestdb --eval \"db.createUser({ user: 'devuser', pwd:  'tTeX%4#MpN0~sQl', roles: ['dbAdmin'] }); db.auth('devuser', 'tTeX%4#MpN0~sQl'); \" "

## Stop MongoDB

After you done playing with this example, you can stop the MongoDB database via:
* docker stop some-mongo
* docker rm some-mongo

## Run

* mvn thorntail:run

For build and deployment to OpenShift, will deploy to active OpenShift project.
* oc project
* mvn clean package fabric8:build fabric8:deploy

Read about the maven fabric8 plugin at https://maven.fabric8.io/

## Use

Since Thorntail apps tend to support one deployment per executable, it
automatically adds a `jboss-web.xml` to the deployment if it doesn't already
exist.  This is used to bind the deployment to the root of the web-server,
instead of using the `.war`'s own name as the application context.

    http://localhost:8080/Hello to show that the application prints a 'Howdy' message, then
    http://localhost:8080/Hello/createDiaryEntry to show that the application can write and read the MongoDB database.

