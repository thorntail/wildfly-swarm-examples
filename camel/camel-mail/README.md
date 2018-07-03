# Camel Mail Example

This example uses camel-mail to send and receive email.

## Project `pom.xml`

The project adds a `<plugin>` to configure `thorntail-maven-plugin` to
create the runnable `.jar`.

    <plugin>
      <groupid>io.thorntail</groupId>
      <artifactId>thorntail-maven-plugin</artifactId>
    </plugin>

To define the needed parts of Thorntail, the following dependencies are added

    <dependency>
        <groupid>io.thorntail</groupId>
        <artifactId>camel-full</artifactId>
    </dependency>

This dependency provides Camel and Java Mail APIs to your application, so the
project does *not* need to specify those.

## Run

You can run it many ways:

* mvn package && java -jar ./target/example-camel-mail-swarm.jar
* mvn thorntail:run
* In your IDE run the `org.wildfly.swarm.Swarm` class

## Use

Once the Swarm container has started, you can test the Camel application by sending an example email message payload to an HTTP endpoint with CURL.

    curl -v -X POST -d 'Hello World!' http://localhost:8080/mail

Camel will send an email via SMTP to a mock mail server running on localhost, using the message body you provided in the CURL request.

After around 5 seconds, you should see a message like the following output to the console:

`Received message from camel@localhost: Hello World!`
