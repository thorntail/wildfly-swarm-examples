/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wildfly.swarm.examples.transactions;

import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author nmcl
 */

@Path("/")
public class MyResource {
    @GET
    @Produces("text/plain")
    public String init() throws Exception {
        return "Active";
    }

    @Path("begincommit")
    @GET
    @Produces("text/plain")
    public String beginCommit() throws Exception {
        UserTransaction txn = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        String value = "Transaction ";

        try {
            txn.begin();

            value += "begun ok";

            try {
                txn.commit();

                value += " and committed ok";
            } catch (final Throwable ex) {
                value += " but failed to commit";
            }
        } catch (final Throwable ex) {
            value += "failed to begin: " + ex.toString();
        }

        return value;
    }

    @Path("beginrollback")
    @GET
    @Produces("text/plain")
    public String beginRollback() throws Exception {
        UserTransaction txn = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        String value = "Transaction ";

        try {
            txn.begin();

            value += "begun ok";

            try {
                txn.rollback();

                value += " and rolled back ok";
            } catch (final Throwable ex) {
                value += " but failed to rollback " + ex.toString();
            }
        } catch (final Throwable ex) {
            value += "failed to begin: " + ex.toString();
        }

        return value;
    }

    @Path("nested")
    @GET
    @Produces("text/plain")
    public String nested() throws Exception {
        UserTransaction txn = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        String value = "Nested transaction ";

        try {
            txn.begin();
            txn.begin();

            value += "support appears to be enabled!";

            try {
                txn.commit();
                txn.commit();
            } catch (final Throwable ex) {
            }
        } catch (final Throwable ex) {
            value += "support is not enabled!";

            txn.commit(); // laziness but should have a try/catch here too
        }

        return value;
    }
}
