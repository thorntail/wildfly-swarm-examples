package org.wildfly.swarm.examples.opentracing.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * @author Pavol Loffay
 */
@Path("/")
public class HelloHandler {

    public HelloHandler() {
    }

    @GET
    @Path("/hello")
    public Response hello(@Context HttpHeaders headers) {
        return Response.status(Response.Status.OK).entity("/hello").build();
    }
}


