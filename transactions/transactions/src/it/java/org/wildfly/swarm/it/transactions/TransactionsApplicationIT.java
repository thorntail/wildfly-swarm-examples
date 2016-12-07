package org.wildfly.swarm.it.transactions;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Bob McWhirter
 * @author Ken Finnigan
 */
@RunWith(Arquillian.class)
public class TransactionsApplicationIT extends AbstractIntegrationTest {

    @Drone
    WebDriver browser;

    @Test
    public void testTransactions() {
        browser.navigate().to("http://localhost:8080/");
        assertThat(browser.getPageSource()).contains("Active");

        browser.navigate().to("http://localhost:8080/begincommit");
        assertThat(browser.getPageSource()).contains("Transaction begun ok and committed ok" );

        browser.navigate().to("http://localhost:8080/beginrollback");
        assertThat(browser.getPageSource()).contains("Transaction begun ok and rolled back ok" );
    }
}
