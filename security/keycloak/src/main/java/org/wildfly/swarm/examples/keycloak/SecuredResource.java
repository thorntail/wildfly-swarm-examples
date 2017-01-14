package org.wildfly.swarm.examples.keycloak;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/secured")
public class SecuredResource {

    @GET
    public String get() {
        return "This is Secured Resource";
    }

}
