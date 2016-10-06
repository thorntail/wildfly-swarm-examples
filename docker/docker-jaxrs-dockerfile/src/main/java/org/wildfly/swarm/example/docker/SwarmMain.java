package org.wildfly.swarm.example.docker;

//import lombok.extern.slf4j.Slf4j;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.config.logging.Level;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.logging.LoggingFraction;

//@Slf4j
public class SwarmMain {
    public static void main(String[] args) throws Exception {
        Container container = new Container();

        Level logLevel = Level.INFO;
        container.fraction(new LoggingFraction()
                .formatter("PATTERN", "%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n")
                .consoleHandler(logLevel, "PATTERN")
                .rootLogger(logLevel, "CONSOLE"));
        container.start();

        JAXRSArchive jaxrsDeployment = ShrinkWrap.create( JAXRSArchive.class );
        jaxrsDeployment.addClass(JaxRsApplication.class);
        jaxrsDeployment.addResource(MyResource.class);
        container.deploy(jaxrsDeployment);
    }
}
