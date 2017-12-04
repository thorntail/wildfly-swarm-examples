package org.wildfly.swarm.it.messaging;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.it.AbstractIntegrationTest;
import org.wildfly.swarm.it.Log;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Bob McWhirter
 */
@RunWith(Arquillian.class)
public class MessagingApplicationIT extends AbstractIntegrationTest {

    @Drone
    WebDriver browser;

    @Test
    public void testIt() throws Exception {

        browser.navigate().to("http://localhost:8080/");
        assertThat(browser.getPageSource()).contains("Howdy!");

        Thread.sleep( 1000 );

        Log log = getStdOutLog();

        assertThatLog(log).hasLineContaining("WFLYJCA0007: Registered connection factory java:/DefaultJMSConnectionFactory");
        assertThatLog(log).hasLineContaining("received: Hello!");
    }
}
