package org.wildfly.swarm.it.jpajaxrscdi;

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
public class JPAJAXRSCDIApplicationIT extends AbstractIntegrationTest {

    @Drone
    WebDriver browser;

    @Test
    public void testIt() {
        browser.navigate().to("http://localhost:8080/");
        assertThat(browser.getPageSource()).contains("{\"id\":1,\"name\":\"Penny\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":2,\"name\":\"Sheldon\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":3,\"name\":\"Amy\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":4,\"name\":\"Leonard\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":5,\"name\":\"Bernadette\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":6,\"name\":\"Raj\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":7,\"name\":\"Howard\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":8,\"name\":\"Priya\"}");
    }
}
