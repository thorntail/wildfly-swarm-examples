package org.wildfly.swarm.examples.jaxrs.cdi;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Bob McWhirter
 */
@RunWith(Arquillian.class)
public class JAXRSApplicationIT extends AbstractIntegrationTest {

    @Drone
    WebDriver browser;

    @Test
    public void testIt() {
        browser.navigate().to("http://localhost:8080/employees");
        assertThat(browser.getPageSource()).contains("{\"id\":1,\"name\":\"emp01\"}");
    }
}
