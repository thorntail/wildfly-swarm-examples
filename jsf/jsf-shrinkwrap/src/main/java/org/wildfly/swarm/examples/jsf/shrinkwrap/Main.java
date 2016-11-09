package org.wildfly.swarm.examples.jsf.shrinkwrap;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.undertow.WARArchive;

/**
 * @author Yoshimasa Tanabe
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Swarm swarm = new Swarm();

        WARArchive deployment = ShrinkWrap.create(WARArchive.class);

        deployment.addClass(Message.class);

        deployment.addAsWebResource(
                new ClassLoaderAsset("index.html", Main.class.getClassLoader()), "index.html");
        deployment.addAsWebResource(
                new ClassLoaderAsset("index.xhtml", Main.class.getClassLoader()), "index.xhtml");

        deployment.addAsWebInfResource(
                new ClassLoaderAsset("WEB-INF/web.xml", Main.class.getClassLoader()), "web.xml");
        deployment.addAsWebInfResource(
                new ClassLoaderAsset("WEB-INF/template.xhtml", Main.class.getClassLoader()), "template.xhtml");

        deployment.addAllDependencies();

        swarm.start().deploy(deployment);

    }
}
