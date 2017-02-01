package org.wildfly.swarm.examples.rar.deployment;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.ejb.EJBFraction;
import org.wildfly.swarm.resource.adapters.RARArchive;
import org.wildfly.swarm.spi.api.JARArchive;

/**
 * @author Ralf Battenfeld
 */
public class Main {

    public static void main(String[] args) throws Exception {
        final ClassLoader classLoader = Main.class.getClassLoader();
        final Swarm swarm = new Swarm();
        swarm.fraction(EJBFraction.createDefaultFraction());
        swarm.start();

        // since Swarm.artifact doesn't support rar archives, we have to manually create a rar
        final RARArchive rarArchive = ShrinkWrap.create(RARArchive.class, "xadisk.rar");
        rarArchive.addAsLibrary(Swarm.artifact("net.java.xadisk:xadisk:jar:1.2.2"));
        rarArchive.addAsManifestResource(new ClassLoaderAsset( "ra.xml", classLoader ), "ra.xml");

        // add the required ironjacamar.xml descriptor
        rarArchive.resourceAdapter(new ClassLoaderAsset( "ironjacamar.xml", classLoader ));
        swarm.deploy(rarArchive);

        final JARArchive appDeployment = ShrinkWrap.create(JARArchive.class);
        appDeployment.merge(Swarm.artifact("net.java.xadisk:xadisk:jar:1.2.2"));
        appDeployment.addClass(FileIOBean.class);

        // Deploy your app
        swarm.deploy(appDeployment);
    }
}
