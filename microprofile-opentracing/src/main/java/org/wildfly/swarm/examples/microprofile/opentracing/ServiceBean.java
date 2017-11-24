package org.wildfly.swarm.examples.microprofile.opentracing;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.opentracing.Traced;

/**
 * @author Pavol Loffay
 */
@Traced
@ApplicationScoped
public class ServiceBean {

  public String method() {
    return ServiceBean.class.getSimpleName() + ".method";
  }
}
