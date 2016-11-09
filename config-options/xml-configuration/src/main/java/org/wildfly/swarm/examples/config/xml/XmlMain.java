package org.wildfly.swarm.examples.config.xml;

import java.net.URL;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * @author Heiko Braun
 * @since 10/11/15
 */
public class XmlMain {

    public static void main(String[] args) throws Exception {

        ClassLoader cl = XmlMain.class.getClassLoader();
        URL xmlConfig = cl.getResource("standalone.xml");

        assert xmlConfig != null : "Failed to load standalone.xml";

        Swarm swarm = new Swarm(false)
                .withXmlConfig(xmlConfig);

        //container.fraction(new LoggingFraction());
        swarm.fraction(new DatasourcesFraction());

        // Start the container
        swarm.start();

        JAXRSArchive appDeployment = ShrinkWrap.create(JAXRSArchive.class);
        appDeployment.addResource(MyResource.class);

        // Deploy your app
        swarm.deploy(appDeployment);
    }
}
