/*
 * #%L
 * Wildfly Camel :: Example :: Camel JPA
 * %%
 * Copyright (C) 2013 - 2014 RedHat
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.wildfly.swarm.examples.camel.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jpa.JpaEndpoint;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.wildfly.extension.camel.CamelAware;
import org.wildfly.swarm.examples.camel.jpa.model.Customer;

@CamelAware
@ApplicationScoped
public class JpaRouteBuilder extends RouteBuilder {

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction userTransaction;

    @Override
    public void configure() throws Exception {
    	
        // Configure our JaxbDataFormat to point at our 'model' package
        JaxbDataFormat jaxbDataFormat = new JaxbDataFormat();
        jaxbDataFormat.setContextPath(Customer.class.getPackage().getName());

        EntityManagerFactory entityManagerFactory = em.getEntityManagerFactory();

        // Configure a JtaTransactionManager by looking up the JBoss transaction manager from JNDI
        JtaTransactionManager transactionManager = new JtaTransactionManager(userTransaction);
        transactionManager.afterPropertiesSet();

        // Configure the JPA endpoint to use the correct EntityManagerFactory and JtaTransactionManager
        JpaEndpoint jpaEndpoint = new JpaEndpoint();
        jpaEndpoint.setCamelContext(getContext());
        jpaEndpoint.setEntityType(Customer.class);
        jpaEndpoint.setEntityManagerFactory(entityManagerFactory);
        jpaEndpoint.setTransactionManager(transactionManager);

        /*
         *  Simple route to consume customer record files from directory input/customers,
         *  unmarshall XML file content to a Customer entity and then use the JPA endpoint
         *  to persist the it to the 'ExampleDS' datasource (see standalone.camel.xml for datasource config).
         */
        from("file://{{jboss.server.data.dir}}/customers")
                .unmarshal(jaxbDataFormat)
                .to(jpaEndpoint)
                .to("log:input?showAll=true");
    }
}
