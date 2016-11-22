package org.wildfly.examples.swarm.main;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * @author Bob McWhirter
 */
public class Main {

    public static void main(String...args) throws Exception {
        System.out.println("Starting custom main: jaxrs-war-main");

        Swarm swarm = new Swarm(args);

        JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);
        archive.addPackage( Main.class.getPackage() );
        archive.addAllDependencies();

        swarm.start().deploy( archive );
    }
}
