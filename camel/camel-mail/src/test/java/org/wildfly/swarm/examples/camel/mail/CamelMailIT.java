/*
 * #%L
 * Wildfly Swarm :: Examples :: Camel Mail
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
package org.wildfly.swarm.examples.camel.mail;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.it.AbstractIntegrationTest;
import org.wildfly.swarm.it.Log;

@RunWith(Arquillian.class)
public class CamelMailIT extends AbstractIntegrationTest {

	@Test
	public void testIt() throws Exception {

		Thread.sleep(5000);

		Log log = getStdOutLog();
		assertThatLog(log).hasLineContaining("Bound camel naming object: java:jboss/camel/context/mail-context");
		assertThatLog(log).hasLineContaining("Bound mail session [java:jboss/mail/greenmail]");

		String response = Request.Post("http://localhost:8080/mail")
			.bodyString("Hello from camel!", ContentType.APPLICATION_FORM_URLENCODED)
			.execute()
			.returnContent()
			.asString();

		Assert.assertEquals(response, "Email sent to user1@localhost");
	}
}
