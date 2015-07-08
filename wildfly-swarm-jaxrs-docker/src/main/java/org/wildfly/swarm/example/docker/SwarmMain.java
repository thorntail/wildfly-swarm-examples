package org.wildfly.swarm.example.docker;

import lombok.extern.slf4j.Slf4j;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSDeployment;
import org.wildfly.swarm.logging.LoggingFraction;

@Slf4j
public class SwarmMain {
    public static void main(String[] args) throws Exception {
        Container container = new Container();

        String logLevel = "INFO";
        container.fraction(new LoggingFraction()
                .formatter("PATTERN", "%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n")
                .consoleHandler(logLevel, "PATTERN")
                .rootLogger(logLevel, "CONSOLE"));
        container.start();

        JAXRSDeployment jaxrsDeployment = new JAXRSDeployment(container);
        jaxrsDeployment.setApplication(JaxRsApplication.class);
        jaxrsDeployment.addResource(MyResource.class);
        container.deploy(jaxrsDeployment);
    }
}
