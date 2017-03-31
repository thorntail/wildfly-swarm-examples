package org.wildfly.swarm.examples.jaxws;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import static org.junit.Assert.assertEquals;

/**
 * Simple set of tests for the HelloWorld Web Service to demonstrate accessing the web service using a client
 *
 * @author lnewson@redhat.com
 * @author Yoshimasa Tanabe
 *
 */
public class ClientIT extends AbstractIntegrationTest {

    /**
     * The path of the WSDL endpoint in relation to the deployed web application.
     */
    private static final String WSDL_PATH = "HelloWorldService?wsdl";

    private HelloWorldService client;

    @Before
    public void setup() {
        try {
            client = new Client("http://localhost:8080/" + WSDL_PATH);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHello() throws Exception {
        // Get a response from the WebService
        final String response = client.sayHello();
        assertEquals("Hello World!", response);
        assertThatLog(getStdOutLog()).hasLineContaining("Checked request context");
    }

    @Test
    public void testHelloName() {
        // Get a response from the WebService
        final String response = client.sayHelloToName("John");
        assertEquals("Hello John!", response);
    }

    @Test
    public void testHelloNames() {
        // Create the array of names for the WebService to say hello to.
        final List<String> names = new ArrayList<>();
        names.add("John");
        names.add("Mary");
        names.add("Mark");

        // Get a response from the WebService
        final String response = client.sayHelloToNames(names);
        assertEquals("Hello John, Mary & Mark!", response);
    }

}