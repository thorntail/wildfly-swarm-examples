package org.wildfly.swarm.examples.jaxrs.cdi.ejb;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GreeterFacade {
    @EJB
    private GreeterBean greeterBean;

    public String hello(String name) {
        String result = greeterBean.hello(name);
        System.out.println("Said: " + result);
        return result;
    }
}
