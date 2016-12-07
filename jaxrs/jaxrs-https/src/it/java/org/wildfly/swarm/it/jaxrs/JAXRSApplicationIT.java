package org.wildfly.swarm.it.jaxrs;

import java.io.IOException;

import org.jboss.arquillian.phantom.resolver.ResolvingPhantomJSDriverService;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Bob McWhirter
 */
public class JAXRSApplicationIT extends AbstractIntegrationTest {

    PhantomJSDriver browser;

    @Before
    public void setup() throws IOException {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();

        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=true"});

        this.browser = new PhantomJSDriver(
                ResolvingPhantomJSDriverService.createDefaultService(capabilities),
                capabilities);
    }

    @Test
    public void testIt() throws InterruptedException {
        browser.navigate().to("https://localhost:8443/");
        assertThat(browser.getPageSource()).contains("Howdy at ");
    }
}
