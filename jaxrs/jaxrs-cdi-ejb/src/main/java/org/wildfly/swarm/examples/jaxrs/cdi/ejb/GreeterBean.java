package org.wildfly.swarm.examples.jaxrs.cdi.ejb;

import javax.ejb.Stateless;

@Stateless
public class GreeterBean {
    public String hello(String name) {
        return "Hello, " + name + "!";
    }
}
