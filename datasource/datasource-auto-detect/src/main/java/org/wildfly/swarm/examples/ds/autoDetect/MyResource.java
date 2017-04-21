package org.wildfly.swarm.examples.ds.autoDetect;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Kylin Soong
 */
@Path("/")
public class MyResource {

    @GET
    @Produces("text/plain")
    public String get() throws NamingException, SQLException {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jboss/datasources/ExampleDS"); // ExampleDS was been created automatically using the auto-detected h2 driver
        Connection conn = ds.getConnection();
        try {
            return "Howdy using connection: " + conn;
        } finally {
            conn.close();
        }
    }
}
