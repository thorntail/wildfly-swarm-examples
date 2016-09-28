/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.wildfly.swarm.examples.jpajaxrscdijta;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

import org.jboss.logging.Logger;

/**
 * This CDI bean observes transactional events.
 * Same event type is observed when transaction succeed or fail and keep track of these successes or failures
 *
 * @author Antoine Sabot-Durand
 */
@ApplicationScoped
public class EmployeeObservers {

    /**
     * Observes {@link Employee} event type in case of transaction failure.
     * Log a failure message and use it to invoke {@link EmployeeService#addRollbackMsg(String)}
     *
     * @param emp payload
     */
    void processTxFailure(@Observes(during = TransactionPhase.AFTER_FAILURE) Employee emp) {
        String msg = "*** An error occurred and deletion of emp # " + emp.getId() + " was roll-backed";
        LOG.info(msg);
        service.addRollbackMsg(msg);
    }

    /**
     * Observes {@link Employee} event type in case of transaction success.
     * Log a success message and use it to invoke {@link EmployeeService#addCommitMsg(String)}
     *
     * @param emp payload
     */
    void processTxSuccess(@Observes(during = TransactionPhase.AFTER_SUCCESS) Employee emp) {
        String msg = "*** Deletion of emp # " + emp.getId() + " was committed";
        LOG.info(msg);
        service.addCommitMsg(msg);
    }

    final static Logger LOG = Logger.getLogger(EmployeeObservers.class);

    @Inject
    EmployeeService service;


}
