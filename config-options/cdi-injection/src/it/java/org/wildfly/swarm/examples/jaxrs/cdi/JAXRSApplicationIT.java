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
    public void testStrings() {
        browser.navigate().to("http://localhost:8080/app/propertyString");
        assertThat(browser.getPageSource()).contains("Hello World");
    }

    @Test
    public void testIntegers() {
        browser.navigate().to("http://localhost:8080/app/propertyInteger");
        assertThat(browser.getPageSource()).contains("50");
    }

    @Test
    public void testBooleans() {
        browser.navigate().to("http://localhost:8080/app/propertyBoolean");
        assertThat(browser.getPageSource()).contains("true");
    }

    @Test
    public void testHealth() {
        browser.navigate().to("http://localhost:8080/app/healthz");
        assertThat(browser.getPageSource()).contains("UP");
    }
}
