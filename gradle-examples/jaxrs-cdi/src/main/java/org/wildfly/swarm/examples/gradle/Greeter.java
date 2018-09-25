package org.wildfly.swarm.examples.gradle;

import javax.enterprise.inject.Default;

/**
 * Example bean.
 */
@Default
public class Greeter {

    public String getGreeting() {
        return "hello: Thorntail + gradle + java";
    }
}
