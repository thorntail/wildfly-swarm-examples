package org.wildfly.swarm.examples.mail;

import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSDeployment;
import org.wildfly.swarm.mail.MailFraction;
import org.wildfly.swarm.mail.SmtpServer;
import org.wildfly.swarm.undertow.WarDeployment;

/**
 * @author Helio Frota
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Container container = new Container();
        
        SmtpServer smtp = new SmtpServer("ExampleName");
        
        container.fraction(new MailFraction()
            .smtpServer(smtp
                .host("add_your_smtp_here")
                .port("25")));
        
        WarDeployment deployment = new JAXRSDeployment(container);
        deployment.getArchive().addClass(Mail.class);
        container.start().deploy(deployment);
        
        System.out.println(smtp.jndiName());

    }
}
