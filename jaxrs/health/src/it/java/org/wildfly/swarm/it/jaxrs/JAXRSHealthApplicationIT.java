package org.wildfly.swarm.it.jaxrs;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Bob McWhirter
 */
@RunWith(Arquillian.class)
public class JAXRSHealthApplicationIT extends AbstractIntegrationTest {

    @Test
    public void testIt() {

        String response = getUrlContents("http://localhost:8080/app/second-health");
        assertThat(response).contains("UP");
    }
}
