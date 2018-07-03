# Contract based testing example

> Please raise any issues found with this example in our JIRA:
> https://issues.jboss.org/browse/THORN

This is an example that demonstrates how consumer driven contracts can be used to verify the contract between a service consumer and a service provider.

If you are not familiar with contract based testing and consumer driven tests in particular, we recommend the excellent Thoughtworks article, that explains the ideas in great detail: http://martinfowler.com/articles/consumerDrivenContracts.html

## Example Layout

The example breaks into two parts:

**Service consumer part**

The consumer verifies it's own service interaction patterns against a mock service as part of it's unit or integration test runs (See `ConsumerTest.java`).

Upon successful completion of the consumer tests, a consumer contract definition is created:

```
{
    "provider": {
        "name": "MyProvider"
    },
    "consumer": {
        "name": "MyConsumer"
    },
    "interactions": [
        {
            "providerState": "Consumer is FOOBAR",
            "description": "A request to say Howdy",
            "request": {
                "method": "POST",
                "path": "/",
                "headers": {
                    "client-name": "FOOBAR"
                }
            },
            "response": {
                "status": 200,
                "body": "Howdy FOOBAR"
            }
        }
    ],
    "metadata": {
        "pact-specification": {
            "version": "2.0.0"
        },
        "pact-jvm": {
            "version": "3.2.0"
        }
    }
}
```
(Taken from `jaxrs-consumer/target/pacts`)

**The service provider part**

The consumer contract definition (`MyConsumer-MyProvider.json`) is shared with the provider. The provider uses this contract to replay the consumer service interaction against it's service implementation.

In this example, it happens through the pact-jvm maven plugin (See `jaxrs-provider/pom.xml`)


```
[INFO] 
[INFO] --- pact-jvm-provider-maven_2.11:3.2.0:verify (verify-pacts) @ example-jaxrs-cdc-provider ---

Loading pact files for provider MyProvider from /Users/hbraun/dev/prj/wildfly-swarm-examples/jaxrs/contract-based-testing/jaxrs-provider/src/pacts
Found 1 pact files

Verifying a pact between MyConsumer and MyProvider
  [Using file /Users/hbraun/dev/prj/wildfly-swarm-examples/jaxrs/contract-based-testing/jaxrs-provider/src/pacts/MyConsumer-MyProvider.json]
  Given Consumer is FOOBAR
  A request to say Howdy
    returns a response which
      has status code 200 (OK)
      has a matching body (OK)

```

## Executing the tests

To run the full cycle of tests, move to the top level directory and execute the integration tests:

```
cd contract-based-testing
mvn clean verify
```

These steps can also be executed for the consumer and provider separately:

```
cd [jaxrs-consumer|jaxrs-provider]
mvn clean verify
```

> **NOTE**: The examples do leverage the maven failsafe plugin. As such it's important execute the `verify` goal, opposed to the other lifecycles of the plugin. `verify` ensures that are relevant steps are executed to teardown the server and cleanup resource references.


## Further resources

- pact-jvm: https://github.com/DiUS/pact-jvm
- consumer driven contracts : http://martinfowler.com/articles/consumerDrivenContracts.html
