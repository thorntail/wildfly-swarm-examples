package org.wildfly.swarm.examples.opentracing.hawkular.jaxrs;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.contrib.global.GlobalTracer;
import io.opentracing.contrib.jaxrs2.server.ServerSpanContext;
import io.opentracing.contrib.jaxrs2.server.Traced;

/**
 * @author Pavol Loffay
 */
@Path("/")
@Produces("text/plain")
public class HelloEndpoint {

    @GET
    @Path("/hello")
    public Response doGet() {
        return Response.ok("Hello from WildFly Swarm!").build();
    }

    @GET
    @Traced(operationName = "otherName")
    @Path("/localspan")
    public Response localSpan(@BeanParam ServerSpanContext serverSpanContext) {

        Tracer tracer = GlobalTracer.get();
        Span localSpanBusinessLogic = tracer.buildSpan("localSpan_businessLogic")
                .asChildOf(serverSpanContext.get())
                .start();

        /**
         * Some business logic
         */
        localSpanBusinessLogic.finish();

        return Response.ok().build();
    }
}
