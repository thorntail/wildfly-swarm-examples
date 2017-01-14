package org.wildfly.swarm.examples.keycloak;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/non-secured")
public class NonSecuredResource {

    @GET
    public String get() {
        return "This is Non Secured Resource";
    }

}
