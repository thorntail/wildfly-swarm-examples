package org.wildfly.swarm.examples.management.console;

import org.fest.assertions.Assertions;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.it.AbstractIntegrationTest;
import org.wildfly.swarm.it.Log;

@RunWith(Arquillian.class)
public class ManagementConsoleIT extends AbstractIntegrationTest {

    @Drone
    WebDriver browser;

    @Test
    public void testIt() throws Exception {
        Log log = getStdOutLog();

        assertThatLog(log).hasLineContaining("Registered web context: '/console'");

        browser.navigate().to("http://127.0.0.1:8080/console");
        Assertions.assertThat(browser.getTitle()).isEqualTo("Management Interface");
    }
}
