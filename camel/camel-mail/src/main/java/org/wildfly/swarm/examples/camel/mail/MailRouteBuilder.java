/*
 * #%L
 * Thorntail :: Examples :: Camel Mail
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

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.mail.Session;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.mail.MailConfiguration;
import org.apache.camel.component.mail.MailEndpoint;

@ApplicationScoped
@ContextName("mail-context")
public class MailRouteBuilder extends RouteBuilder {

    @Resource(mappedName = "java:jboss/mail/greenmail")
    private Session mailSession;

    @Override
    public void configure() throws Exception {
        MailEndpoint sendMailEndpoint = getContext().getEndpoint("smtp://localhost:10025", MailEndpoint.class);
        MailConfiguration config = sendMailEndpoint.getConfiguration();
        config.setSession(mailSession);

        // Handles a POST request to http://localhost/mail where the request body is used for the mail message
        from("undertow://http://localhost/mail?httpMethodRestrict=POST")
        .process(exchange -> {
            exchange.getOut().setHeader("from", "camel@localhost");
            exchange.getOut().setHeader("to", "user1@localhost");
            exchange.getOut().setHeader("subject", "Hello from camel");
            exchange.getOut().setHeader("message", exchange.getIn().getBody(String.class));
        })
        .to(sendMailEndpoint)
        // Return some content back to the HTTP client
        .setBody(simple("Email sent to ${header.to}"));

        // Poll 'user1' mailbox and output any new messages (old messages are deleted once processed)
        from("pop3://localhost:10110?username=user1&password=password&delete=true&consumer.delay=5000")
        .log("Received message from ${header.from}: ${header.message}");
    }

}
