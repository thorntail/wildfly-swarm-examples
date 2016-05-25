package org.wildfly.swarm.examples.camel.jpa;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.it.AbstractIntegrationTest;
import org.wildfly.swarm.it.Log;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Arquillian.class)
public class CamelJpaIT extends AbstractIntegrationTest {

    @Drone
    WebDriver browser;

    @Test
    public void testCamelJPA() throws Exception {
    	
		Log log = getStdOutLog();
		assertThatLog(log).hasLineContaining("Bound data source [java:jboss/datasources/JpaExampleDS]");
		assertThatLog(log).hasLineContaining("Bound camel naming object: java:jboss/camel/context/camel-1");
		assertThatLog(log).hasLineContaining("Route: route1 started and consuming from: Endpoint[file://");

		browser.navigate().to("http://localhost:8080/example-camel-jpa/customers");
        assertThat(browser.getPageSource()).contains("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"");
    }
}
