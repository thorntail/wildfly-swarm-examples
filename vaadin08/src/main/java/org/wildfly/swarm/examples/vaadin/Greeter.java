package org.wildfly.swarm.examples.vaadin;

import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author Ken Finnigan
 */
@ApplicationScoped
public class Greeter {
    public String greet() {
        return "Howdy at " + LocalDateTime.now();
    }
}
