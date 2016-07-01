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
package org.wildfly.swarm.examples.camel.cxf.jaxws;

import org.wildfly.swarm.container.Container;

public class Main {
    public static void main(String... args) throws Exception {
        // start with eager HTTP ports
        new Container().start(true).deploy();
    }
}
