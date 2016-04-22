/*
 * #%L
 * Wildfly Swarm :: Examples :: Camel JMS
 * %%
 * Copyright (C) 2016 RedHat
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.wildfly.swarm.examples.camel.jms;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.it.AbstractIntegrationTest;
import org.wildfly.swarm.it.Log;

@RunWith(Arquillian.class)
public class CamelJmsIT extends AbstractIntegrationTest {

    @Drone
    WebDriver browser;

    @Test
    public void testIt() throws Exception {

        browser.navigate().to("http://localhost:8080/hello?name=bob");

        Thread.sleep(1000);

        Log log = getStdOutLog();
        assertThatLog(log).hasLineContaining("WFLYJCA0007: Registered connection factory java:/DefaultJMSConnectionFactory");
        assertThatLog(log).hasLineContaining("Bound camel naming object: java:jboss/camel/context/jms-context");
        assertThatLog(log).hasLineContaining("TestQueue received message: Hello bob");
    }
}
