package org.wildfly.swarm.examples.mail;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Helio Frota
 */
@Path("/send")
public class Mail {
    
    @GET
    public String send() throws Exception {

        InitialContext ctx = new InitialContext();
        Session session = (Session) ctx.lookup("java:jboss/mail/ExampleName");

        Message message = new MimeMessage(session);
        message.setFrom();
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("example@test.org", false));
        message.setSubject("example");
        message.setSentDate(new Date());
        message.setContent("example message.", "text/html; charset=UTF-8");
        Transport.send(message);
        
        return "mail sent.";
    }
}
