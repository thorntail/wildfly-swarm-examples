package org.wildfly.swarm.examples.mail;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.mail.MailFraction;
import org.wildfly.swarm.mail.SmtpServer;

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
        
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addClass(Main.class);
        deployment.addAllDependencies();
        container.start().deploy(deployment);

        System.out.println(smtp.jndiName());

    }
}
