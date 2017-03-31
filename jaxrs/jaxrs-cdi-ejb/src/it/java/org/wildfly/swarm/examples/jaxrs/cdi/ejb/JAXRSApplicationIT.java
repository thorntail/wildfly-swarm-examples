package org.wildfly.swarm.examples.jaxrs.cdi.ejb;

import org.apache.http.client.fluent.Request;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Arquillian.class)
public class JAXRSApplicationIT extends AbstractIntegrationTest {
    @Test
    public void empty() throws Exception {
        String result = Request.Get("http://localhost:8080/greetings?name=Ladicek").execute().returnContent().asString();
        assertThat(result).isEqualTo("Hello, Ladicek!");
        assertThatLog(getStdOutLog()).hasLineContaining("Said: Hello, Ladicek!");
    }
}
