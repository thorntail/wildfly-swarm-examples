package org.wildfly.swarm.examples.camel.cxf.jaxrs;

import org.fest.assertions.Assertions;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.it.AbstractIntegrationTest;
import org.wildfly.swarm.it.Log;

@RunWith(Arquillian.class)
public class CamelCxfJaxrsIT extends AbstractIntegrationTest {

    @Drone
    WebDriver browser;

    @Test
    public void testIt() throws Exception {
        Log log = getStdOutLog();
        assertThatLog( log ).hasLineContaining( "Bound camel naming object: java:jboss/camel/context/cxfrs-camel-context" );

        browser.navigate().to("http://localhost:8080/camel/greeting/hello/Bob");
        Assertions.assertThat(browser.getPageSource()).contains("Hello Bob");
    }
}
