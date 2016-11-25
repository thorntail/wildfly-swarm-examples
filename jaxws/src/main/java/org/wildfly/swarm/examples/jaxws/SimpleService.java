package org.wildfly.swarm.examples.jaxws;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.xml.ws.WebServiceContext;

@RequestScoped
public class SimpleService {
    @Resource
    private WebServiceContext webServiceContext;

    public void checkRequestContext() {
        if (webServiceContext != null) {
            System.out.println("Checked request context");
        }
    }
}
