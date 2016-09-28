/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.wildfly.swarm.examples.jpajaxrscdijta;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * This Service bean demonstrate various JPA manipulations of {@link Employee}
 *
 * @author Antoine Sabot-Durand
 */
@ApplicationScoped
public class EmployeeService {

    /**
     * @return all the {@link Employee} in the db
     */
    public List<Employee> getAll() {
        return em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
    }

    /**
     * Remove {@link Employee} one by one and throws an exception at a given point
     * to simulate a real error and test Transaction bahaviour
     *
     * @param failOn index where the method should fail
     * @throws IllegalStateException when removing {@link Employee} at given index
     */
    public void remTx(int failOn) {
        resetMsgLists();
        for (Integer i = 1; i < 9; i++) {
            Employee employee = em.find(Employee.class, i);
            empvt.fire(employee);
            em.remove(employee);
            if (i >= failOn) {
                throw new IllegalStateException("*** On purpose failure during removal to test Rollback *** ");
            }
        }
    }

    public void resetMsgLists() {
        commitMsg.clear();
        rollbackMsg.clear();
    }

    /**
     * Add a message to the commit messages list
     *
     * @param msg to add
     */
    public void addCommitMsg(String msg) {
        commitMsg.add(msg);
    }

    /**
     * Add a message to the roll back messages list
     *
     * @param msg to add
     */
    public void addRollbackMsg(String msg) {
        rollbackMsg.add(msg);
    }

    /**
     * @return commit messages
     */
    public List<String> getCommitMsg() {
        return commitMsg;
    }

    /**
     * @return rollback messages
     */
    public List<String> getRollbackMsg() {
        return rollbackMsg;
    }

    @Inject
    Event<Employee> empvt;

    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    private List<String> commitMsg = new ArrayList<>();

    private List<String> rollbackMsg = new ArrayList<>();
}
