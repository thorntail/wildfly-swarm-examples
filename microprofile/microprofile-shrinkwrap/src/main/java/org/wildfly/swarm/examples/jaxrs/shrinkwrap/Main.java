package org.wildfly.swarm.examples.jaxrs.shrinkwrap;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.logging.LoggingFraction;

/**
 * @author Bob McWhirter
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Swarm swarm = new Swarm();
        swarm.fraction(LoggingFraction.createDebugLoggingFraction());

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "myapp.war");
        deployment.addClass(MyResource.class);
        deployment.addClass(NotFoundExceptionMapper.class);
        deployment.addAllDependencies();
        swarm.start().deploy(deployment);
    }
}
