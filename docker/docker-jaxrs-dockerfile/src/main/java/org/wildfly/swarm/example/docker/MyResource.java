package org.wildfly.swarm.example.docker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/resource")
public class MyResource {

    @GET
    @Produces("text/plain")
    public String foo() {
        return "bar\n";
    }
}
