package org.wildfly.swarm.examples.jpajaxrscdi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Ken Finnigan
 */
@Path("/")
public class EmployeeResource {

    @Inject
    PersistenceHelper helper;

    @GET
    @Produces("application/json")
    public Employee[] get() {
        return helper.getEntityManager().createNamedQuery("Employee.findAll", Employee.class).getResultList().toArray(new Employee[0]);
    }
}
