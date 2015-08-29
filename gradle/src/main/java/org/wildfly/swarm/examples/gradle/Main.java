package org.wildfly.swarm.examples.gradle;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * @author helio frota
 *
 */
public class Main {

    public static void main(String... args) throws Exception {

        Container container = new Container();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addClass(HelloRest.class);
        deployment.addAllDependencies();
        container.start().deploy(deployment);

    }
}
