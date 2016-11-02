package org.wildfly.swarm.it.jaxrs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import au.com.dius.pact.consumer.ConsumerPactTest;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;

import static org.junit.Assert.assertEquals;

/**
 * Declares the expected interactions and starts a mock server.
 * The actual client code is then tested in {@link #runTest(String)}.
 *
 * @author Heiko Braun
 * @since 03/12/15
 */
public class ConsumerTest extends ConsumerPactTest {

    @Override
    protected PactFragment createFragment(PactDslWithProvider builder) {
        Map<String,String> requestHeader = new HashMap<>();
        requestHeader.put("client-name", "FOOBAR");

        return builder
                .given("Consumer is FOOBAR")
                .uponReceiving("A request to say Howdy")
                    .path("/")
                    .method("POST")
                    .headers(requestHeader)
                .willRespondWith()
                    .status(200)
                    .body("Howdy FOOBAR")
                .toFragment();
    }


    @Override
    protected String providerName() {
        return "MyProvider";
    }

    @Override
    protected String consumerName() {
        return "MyConsumer";
    }

    @Override
    protected void runTest(String url) throws IOException {
        assertEquals("Howdy FOOBAR", new ProviderClient(url).hello("FOOBAR"));
    }
}