package org.wildfly.swarm.examples.zipkin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author Pavol Loffay
 */
@Path("/hello")
public class HelloEndpoint {

    @GET
    @Produces("text/plain")
    public Response doGet() {
        return Response.ok("Hello from Thorntail!").build();
    }
}
