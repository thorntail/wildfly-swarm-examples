package org.wildfly.swarm.examples.jaas.basic;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * @author Ken Finnigan
 */
@Path("/")
@ApplicationScoped
public class EmployeeResource {

    @PersistenceContext
    EntityManager em;
    @Context 
    SecurityContext securityContext;

    @GET
    @Produces("application/json")
    public Employee[] get() {
        return securityContext.isUserInRole("admin")
            ? em.createNamedQuery("Employee.findAll", Employee.class).getResultList().toArray(new Employee[0])
            : new Employee[0];
    }
}
