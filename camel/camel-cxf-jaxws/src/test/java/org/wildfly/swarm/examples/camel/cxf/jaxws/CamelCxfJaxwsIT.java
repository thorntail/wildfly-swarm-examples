package org.wildfly.swarm.examples.camel.cxf.jaxws;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.it.AbstractIntegrationTest;
import org.wildfly.swarm.it.Log;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Arquillian.class)
public class CamelCxfJaxwsIT extends AbstractIntegrationTest {

    @Drone
    WebDriver browser;

    @Test
    public void testIt() throws Exception {
        Log log = getStdOutLog();
        assertThatLog( log ).hasLineContaining( "Bound camel naming object: java:jboss/camel/context/cxfws-camel-context" );

        browser.navigate().to("http://localhost:8080/camel/greeting?wsdl");
        assertThat(browser.getPageSource()).contains("GreetingServiceSoapBinding");

        QName qname = new QName("http://wildfly.swarm.examples.camel.cxf.jaxws", "GreetingService");
        Service service = Service.create(new URL("http://localhost:8080/camel/greeting?wsdl"), qname);
        GreetingService greetingService = service.getPort(GreetingService.class);
        Assert.assertEquals("Hello Kermit", greetingService.greet("Kermit"));
    }
}
