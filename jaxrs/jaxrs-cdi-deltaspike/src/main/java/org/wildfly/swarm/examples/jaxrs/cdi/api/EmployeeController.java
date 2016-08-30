package org.wildfly.swarm.examples.jaxrs.cdi.api;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.wildfly.swarm.examples.jaxrs.cdi.domain.model.Employee;
import org.wildfly.swarm.examples.jaxrs.cdi.domain.service.EmployeeService;

/**
 * @author Yoshimasa Tanabe
 */
@ApplicationScoped
@Path("/employees")
public class EmployeeController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> findAll() {
        EmployeeService employeeService = BeanProvider.getContextualReference(EmployeeService.class, false);

        List<Employee> results = employeeService.findAll();
        System.err.println(results);
        return results;
    }

}
