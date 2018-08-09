package org.wildfly.swarm.it.vaadin;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Bob McWhirter
 */
@RunWith(Arquillian.class)
public class VaadinApplicationIT extends AbstractIntegrationTest {

    @Drone
    PhantomJSDriver browser;

    @Test
    public void testIt() throws Exception {
        browser.manage().window().setSize( new Dimension(1024, 768));
        browser.navigate().to("http://localhost:8080/");

        browser.findElementByClassName( "v-button" ).click();
        WebElement notification = browser.findElementByClassName("v-Notification");

        assertThat( notification.getText() ).contains( "Howdy at" );
    }
}
