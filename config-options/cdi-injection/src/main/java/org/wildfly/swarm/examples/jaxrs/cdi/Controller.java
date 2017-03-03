package org.wildfly.swarm.examples.jaxrs.cdi;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.wildfly.swarm.health.Health;
import org.wildfly.swarm.health.HealthStatus;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

/**
 * @author Heiko Braun
 */
@ApplicationScoped
@Path("/app")
public class Controller {

    @Inject
    @ConfigurationValue("some.string.property")
    String stringProperty;

    @Inject
    @ConfigurationValue("some.integer.property")
    Integer intProperty;

    @Inject
    @ConfigurationValue("some.boolean.property")
    Boolean boolProperty;

    @GET
    @Path("/propertyString")
    @Produces(MediaType.TEXT_PLAIN)
    public String getStringProperty() {
        if (null == stringProperty) throw new RuntimeException("config property not initialised");
        return stringProperty;
    }

    @GET
    @Path("/propertyInteger")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIntegerProperty() {
        if (null == intProperty) throw new RuntimeException("config property not initialised");
        return String.valueOf(intProperty);
    }

    @GET
    @Path("/propertyBoolean")
    @Produces(MediaType.TEXT_PLAIN)
    public String getBoolProperty() {
        if (null == boolProperty) throw new RuntimeException("config property not initialised");
        return String.valueOf(boolProperty);
    }

    @GET
    @Path("/healthz")
    @Health
    public HealthStatus checkSomethingElse() {
        return HealthStatus
                .named("myCheck")
                .up()
                .withAttribute("date", new Date().toString())
                .withAttribute("time", System.currentTimeMillis());
    }

}
