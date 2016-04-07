package org.wildfly.swarm.it.jaxrs;

import org.junit.Test;
import org.kohsuke.ajp.client.SimpleAjpClient;
import org.kohsuke.ajp.client.TesterAjpMessage;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Yoshimasa Tanabe
 */
public class JAXRSApplicationIT extends AbstractIntegrationTest {

    @Test
    public void testIt() throws Exception {
        SimpleAjpClient ac = new SimpleAjpClient();
        ac.connect("localhost", 8009);

        TesterAjpMessage forwardMessage = ac.createForwardMessage("/");
        forwardMessage.end();

        ac.sendMessage(forwardMessage);

        assertThat(ac.readMessage().readString()).contains("Howdy at ");

        ac.disconnect();
    }

}

