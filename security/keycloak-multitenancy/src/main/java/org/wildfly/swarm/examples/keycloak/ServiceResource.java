package org.wildfly.swarm.examples.keycloak;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Path("/secured")
public class ServiceResource {

    @GET
    public String get(@Context SecurityContext sc) {
        return "Hi " + sc.getUserPrincipal().getName() + ", this is Secured Resource";
    }

}
