package org.wildfly.examples.swarm.logstash;

import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class MyResource {

  private static final Logger LOG = Logger.getLogger(MyResource.class);

  @GET
  @Path("/debug")
  public String debug() {
    LOG.debug("This is DEBUG message");
    return "debug";
  }

  @GET
  @Path("/info")
  public String info() {
    LOG.info("This is INFO message");
    return "info";
  }

  @GET
  @Path("/warn")
  public String warn() {
    LOG.warn("This is WARN message");
    return "warn";
  }

  @GET
  @Path("/error")
  public String error() {
    LOG.error("This is ERROR message");
    return "error";
  }

  @GET
  @Path("/exception")
  public String exception() {
    throw new RuntimeException("uh-oh");
  }

}
