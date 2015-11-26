package org.wildfly.swarm.examples.messaging;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Bob McWhirter
 */
@Path("/")
public class MyResource {

    public static final String MY_TOPIC = "/jms/topic/my-topic";

    @GET
    @Produces("text/plain")
    public String get() throws NamingException, JMSException {
        Context ctx = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
        Destination destination = (Destination) ctx.lookup(MY_TOPIC);

        try (
                JMSContext context = factory.createContext()
        ) {
            context.createProducer().send(destination, "Hello!");
        }
        return "Howdy!";
    }
}
