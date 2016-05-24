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

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.ConnectionFactory;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.jms.JmsComponent;

@ApplicationScoped
@ContextName("jms-context")
public class JmsRouteBuilder extends RouteBuilder {

    @Resource(mappedName = "java:jboss/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Override
    public void configure() throws Exception {
        JmsComponent component = new JmsComponent();
        component.setConnectionFactory(connectionFactory);

        getContext().addComponent("jms", component);

        from("undertow:http://localhost/hello")
        .setBody(simple("Hello ${header.name}"))
        .convertBodyTo(String.class)
        .to("jms:queue:TestQueue");

        from("jms:queue:TestQueue")
        .log("TestQueue received message: ${body}");

    }
}
