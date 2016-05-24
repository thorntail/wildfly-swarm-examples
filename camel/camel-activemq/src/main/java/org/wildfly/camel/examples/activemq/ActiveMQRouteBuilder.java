/*
 * #%L
 * Wildfly Camel :: Example :: Camel ActiveMQ
 * %%
 * Copyright (C) 2013 - 2014 RedHat
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
package org.wildfly.camel.examples.activemq;

import javax.enterprise.context.ApplicationScoped;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.builder.RouteBuilder;
import org.wildfly.extension.camel.CamelAware;

@CamelAware
@ApplicationScoped
public class ActiveMQRouteBuilder extends RouteBuilder {

    private static String BROKER_URL = "vm://localhost?broker.persistent=false&broker.useJmx=false&broker.useShutdownHook=false";

    @Override
    public void configure() throws Exception {

        /**
         * Configure the ActiveMQ component to use an embedded VM transport broker
         */
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setBrokerURL(BROKER_URL);
        getContext().addComponent("activemq", activeMQComponent);

        /**
         * This route reads files placed within JBOSS_HOME/standalone/data/orders
         * and places them onto ActiveMQ queue 'ordersQueue'
         */
        from("file://{{jboss.server.data.dir}}/orders")
        .convertBodyTo(String.class)
        // Remove headers to ensure we end up with unique file names being generated in the next route
        .removeHeaders("*")
        .to("activemq:queue:OrdersQueue");

        /**
         * This route consumes messages from the 'ordersQueue'. Then, based on the
         * message payload XML content it uses a content based router to output
         * orders into appropriate country directories
         */
        from("activemq:queue:OrdersQueue")
            .choice()
                .when(xpath("/order/customer/country = 'UK'"))
                    .log("Sending order ${file:name} to the UK")
                    .to("file:{{jboss.server.data.dir}}/orders/processed/uk")
                .when(xpath("/order/customer/country = 'US'"))
                    .log("Sending order ${file:name} to the US")
                    .to("file:{{jboss.server.data.dir}}/orders/processed/us")
                .otherwise()
                    .log("Sending order ${file:name} to another country")
                    .to("file://{{jboss.server.data.dir}}/orders/processed/other");
    }
}
