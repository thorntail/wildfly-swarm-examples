package org.wildfly.swarm.examples.microprofile.opentracing;

import io.opentracing.Tracer;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.opentracing.ClientTracingRegistrar;
import org.eclipse.microprofile.opentracing.Traced;

/**
 * @author Pavol Loffay
 */
@Path("/")
public class MyResource {

  @Inject
  private Tracer tracer;

  @Inject
  private ServiceBean tracedBean;

  @GET
  @Produces("text/plain")
  public String get(){
    return "Hey!";
  }

  @GET
  @Path("/chaining")
  @Produces("text/plain")
  public String chaining() {
    ClientBuilder clientBuilder = ClientBuilder.newBuilder();
    ClientTracingRegistrar.configure(clientBuilder);

    Client client = clientBuilder.build();
    Response response = client.target("http://localhost:8080/")
        .request()
        .get();
    return "Chaining => " + response.readEntity(String.class);
  }

  @GET
  @Path("/service")
  @Traced(operationName = "Service_invocation")
  @Produces("text/plain")
  public String service(){
    return tracedBean.method();
  }

  @GET
  @Traced(false)
  @Path("/health")
  @Produces("text/plain")
  public Response health(){
    return Response.ok().build();
  }
}
