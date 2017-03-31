package org.wildfly.swarm.examples.jaxrs.cdi.ejb;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/greetings")
public class GreetingsResource {
    @Inject
    private GreeterFacade greeterFacade;

    @GET
    public String get(@QueryParam("name") String name) {
        return greeterFacade.hello(name);
    }
}
