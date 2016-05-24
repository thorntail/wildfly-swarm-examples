package org.wildfly.swarm.examples.camel.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.wildfly.swarm.examples.camel.jpa.model.Customer;

@Path("/customers")
public class CustomerResource {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces("application/json")
    public List<Customer> get() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);
        query.select(query.from(Customer.class));
        return em.createQuery(query).getResultList();
    }
}
