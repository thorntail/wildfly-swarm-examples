package org.wildfly.examples.swarm.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;

/**
 * @author Bob McWhirter
 */
@Path("/")
@Api(value = "/", description = "Get the time", tags = "time")
public class MyResource {

    @GET
    @Produces("text/plain")
    @ApiOperation(value = "Get the current time",
            notes = "Returns the time as a string, along with some JDK class name for good measure",
            response = String.class
    )
    public String get() {
        // Prove we can use an external dependency and weird JDK classes.
        return "Howdy at " + new DateTime() + ".  Have a JDK class: " + javax.security.auth.login.LoginException.class.getName();
    }
}
