/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.wildfly.swarm.it.jpajaxrscdijta;


import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test different rest call on transactional Rest endpoint
 *
 * @author antoine Sabot-Durand
 */
@RunWith(Arquillian.class)
public class JPAJAXRSCDIJTAApplicationIT extends AbstractIntegrationTest {

    /**
     * Test full employees list request
     */
    @Test
    @InSequence(1)
    public void testAll() {
        browser.navigate().to("http://localhost:8080/all");
        assertThat(browser.getPageSource()).contains("{\"id\":1,\"name\":\"Penny\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":2,\"name\":\"Sheldon\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":3,\"name\":\"Amy\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":4,\"name\":\"Leonard\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":5,\"name\":\"Bernadette\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":6,\"name\":\"Raj\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":7,\"name\":\"Howard\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":8,\"name\":\"Priya\"}");
    }

    /**
     * Test removal with exception triggering rollback
     */
    @Test
    @InSequence(2)
    public void testRollBack() {
        try {
            browser.navigate().to("http://localhost:8080/remWithRollback");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalStateException.class);
            assertThat(e).hasMessage("*** On purpose failure during removal to test Rollback ***");
        }
        assertThat(browser.getPageSource()).contains("*** On purpose failure during removal to test Rollback ***");

        browser.navigate().to("http://localhost:8080/rollbackMsg");
        assertThat(browser.getPageSource().contains("5"));

        browser.navigate().to("http://localhost:8080/commitMsg");
        assertThat(browser.getPageSource().contains("0"));


        testAll();
    }

    /**
     * Test removal with exception excluded from rollback
     */
    @Test
    @InSequence(3)
    public void testWithoutRollBack() {
        try {
            browser.navigate().to("http://localhost:8080/remWithoutRollback");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalStateException.class);
            assertThat(e).hasMessage("*** On purpose failure during removal to test Rollback ***");
        }
        assertThat(browser.getPageSource()).contains("*** On purpose failure during removal to test Rollback ***");
        browser.navigate().to("http://localhost:8080/rollbackMsg");
        assertThat(browser.getPageSource().contains("0"));
        browser.navigate().to("http://localhost:8080/commitMsg");
        assertThat(browser.getPageSource().contains("3"));
        browser.navigate().to("http://localhost:8080/all");
        assertThat(browser.getPageSource()).doesNotContain("{\"id\":1,\"name\":\"Penny\"}");
        assertThat(browser.getPageSource()).doesNotContain("{\"id\":2,\"name\":\"Sheldon\"}");
        assertThat(browser.getPageSource()).doesNotContain("{\"id\":3,\"name\":\"Amy\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":4,\"name\":\"Leonard\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":5,\"name\":\"Bernadette\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":6,\"name\":\"Raj\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":7,\"name\":\"Howard\"}");
        assertThat(browser.getPageSource()).contains("{\"id\":8,\"name\":\"Priya\"}");
    }

    @Drone
    WebDriver browser;


}
