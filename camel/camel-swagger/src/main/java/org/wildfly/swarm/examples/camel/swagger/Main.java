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
package org.wildfly.swarm.examples.camel.swagger;

import org.wildfly.swarm.Swarm;
import javax.ws.rs.core.MediaType;

import org.apache.camel.builder.RouteBuilder;
import org.wildfly.swarm.camel.core.CamelCoreFraction;

public class Main {

    public static void main(String... args) throws Exception {

        new Swarm()
            .fraction(new CamelCoreFraction().addRouteBuilder("rest-context", new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    restConfiguration().component("undertow")
                    .contextPath("rest")
                    .host("localhost")
                    .port(8080)
                    .apiContextPath("/api-doc")
                    .apiProperty("api.title", "User API").apiProperty("api.version", "1.2.3")
                    .apiProperty("cors", "true");
                
                    rest("/hello")
                    .get("/{name}").description("A user object").outType(User.class).to("direct:hello")
                    .produces(MediaType.APPLICATION_JSON)
                    .consumes(MediaType.APPLICATION_JSON);
                
                    from("direct:hello").transform(simple("Hello ${header.name}"));
                }
             }))
            .start(true).deploy();
    }
}
