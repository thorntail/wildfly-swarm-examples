package org.wildfly.swarm.examples.microprofile.opentracing;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Pavol Loffay
 */
@ApplicationPath("/")
public class MyApplication extends Application {

  public MyApplication() {
  }
}

