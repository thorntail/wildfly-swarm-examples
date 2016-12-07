/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.swarm.examples.arjuna;

import java.util.Hashtable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.arjuna.ats.arjuna.AtomicAction;
import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.arjuna.common.arjPropertyManager;
import com.arjuna.ats.arjuna.coordinator.CheckedAction;
import com.arjuna.ats.arjuna.coordinator.CheckedActionFactory;

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

    @GET
    @Path("atomicaction")
    @Produces("text/plain")
    public String atomicaction() throws Exception
    {
	String value = "problem!";

	try
	{
	    arjPropertyManager.getCoordinatorEnvironmentBean()
		.setCheckedActionFactory(new CheckedActionFactory() {

			@Override
			    public CheckedAction getCheckedAction(Uid txId,
								  String actionType) {
			    System.out.println("MyEJB::getCheckedAction called");
			    return new CheckedAction() {
				@Override
				    public void check(boolean isCommit, Uid actUid,
						      Hashtable list) {
				    System.out.println("MyResource::check called");
				}
			    };
			}
		    });

	    AtomicAction A = new AtomicAction();

	    A.begin();

	    value = "Begin "+A;

	    A.commit();

	    value += "\nCommitted "+A;
	}
	catch (final Throwable x)
	{
	    value += x;
	}

	return value;
    }

    @Path("begincommit")
    @GET
    @Produces("text/plain")
    public String beginCommit() throws Exception {
	AtomicAction txn = new AtomicAction();
	String value = "Transaction ";

	try
	{
	    txn.begin();

	    value += "begun ok";

	    try
	    {
		txn.commit();

		value += " and committed ok";
	    }
	    catch (final Throwable ex)
	    {
		value += " but failed to commit";
	    }
	}
	catch (final Throwable ex)
	{
	    value += "failed to begin: "+ex.toString();
        }

        return value;
    }

    @Path("beginrollback")
    @GET
    @Produces("text/plain")
    public String beginRollback() throws Exception {
	AtomicAction txn = new AtomicAction();
	String value = "Transaction ";

	try
	{
	    txn.begin();

	    value += "begun ok";

	    try
	    {
		txn.abort();

		value += " and rolled back ok";
	    }
	    catch (final Throwable ex)
	    {
		value += " but failed to rollback "+ex.toString();
	    }
	}
	catch (final Throwable ex)
	{
	    value += "failed to begin: "+ex.toString();
        }

        return value;
    }

    @Path("nested")
    @GET
    @Produces("text/plain")
    public String nested() throws Exception {
	AtomicAction txn = new AtomicAction();
	String value = "Nested transaction ";

	try
	{
	    txn.begin();

	    AtomicAction txn2 = new AtomicAction();

	    txn2.begin();

	    value += " " + txn2+" started!";

	    try
	    {
		txn2.commit();
		txn.commit();
	    }
	    catch (final Throwable ex)
	    {
	    }
	}
	catch (final Throwable ex)
	{
	    value += "failed!";

	    txn.commit(); // laziness but should have a try/catch here too
        }

        return value;
    }
}
