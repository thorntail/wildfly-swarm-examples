package org.wildfly.swarm.examples.jaxrs.cdi.beanvalidation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Arquillian.class)
public class JAXRSApplicationIT extends AbstractIntegrationTest {

    private String listBooks() throws IOException {
        return Request.Get("http://localhost:8080/books").execute().returnContent().asString();
    }

    @Test
    @InSequence(1)
    public void empty() throws IOException {
        assertThat(listBooks()).isEqualTo("[]");
    }

    @Test
    @InSequence(2)
    public void putInvalid() throws IOException {
        HttpResponse response = Request.Put("http://localhost:8080/books?isbn=bad&title=Foo&author=Bar").execute()
                .returnResponse();
        assertThat(response.getStatusLine().getStatusCode()).isGreaterThanOrEqualTo(400);
        String error = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        assertThat(error).contains("ISBN must be valid");
        assertThat(listBooks()).isEqualTo("[]");
    }

    @Test
    @InSequence(3)
    public void putValid() throws IOException {
        String response = Request.Put("http://localhost:8080/books?isbn=1234567890&title=Foo&author=Bar").execute()
                .returnContent().asString();
        assertThat(response).contains("\"title\":\"Foo\"");
        assertThat(response).contains("\"author\":\"Bar\"");
        assertThat(listBooks()).isNotEqualTo("[]").contains("\"isbn\":\"1234567890\"");
    }
}
