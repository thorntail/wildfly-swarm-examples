package org.wildfly.swarm.examples.messaging.clustering;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.messaging.MessagingFraction;
import org.wildfly.swarm.msc.ServiceActivatorArchive;
import org.wildfly.swarm.spi.api.JARArchive;

/**
 * @author Bob McWhirter
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Swarm swarm = new Swarm();

        swarm.fraction(MessagingFraction.createDefaultFraction()
                .defaultServer((s) -> {
                    s.enableClustering();
                    s.jmsTopic("my-topic");
                    s.jmsQueue("my-queue");
                }));

        // Start the container
        swarm.start();

        JAXRSArchive appDeployment = ShrinkWrap.create(JAXRSArchive.class);
        appDeployment.addResource(MyResource.class);

        // Deploy your app
        swarm.deploy(appDeployment);

        JARArchive deployment = ShrinkWrap.create(JARArchive.class);
        deployment.addClass(MyService.class);
        deployment.as(ServiceActivatorArchive.class).addServiceActivator(MyServiceActivator.class);

        // Deploy the services
        swarm.deploy(deployment);

    }
}
