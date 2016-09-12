package org.wildfly.swarm.examples.jdd;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Created by rafaelszp on 9/8/16.
 */
@ApplicationPath("/api")
@ApplicationScoped
public class JAXRSActivator extends Application{
    @PersistenceContext
    @Produces
    private EntityManager entityManager;
}
