package org.wildfly.swarm.examples.gradle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author helio frota
 *
 */
@Path("/")
public class HelloRest {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {

        return "hello: Thorntail + gradle + java";
    }

}
