package org.wildfly.swarm.examples.ds.autoDetect;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourceArchive;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * @author Kylin Soong
 */
public class Main {

    private static String URL = "jdbc:h2:file:" + System.getProperty("java.io.tmpdir") + "/test";
    private static String USER = "sa";
    private static String PASSWORD = "sa";

    public static void main(String[] args) throws Exception {

        Swarm swarm = new Swarm();
        swarm.start();

        DatasourceArchive dsArchive = ShrinkWrap.create(DatasourceArchive.class);
        //the 'h2' are auto-detected, and registered dynamic
        dsArchive.dataSource("TestDS", ds -> ds.driverName("h2").connectionUrl(URL).userName(USER).password(PASSWORD)); 
        swarm.deploy(dsArchive);

        JAXRSArchive restArchive = ShrinkWrap.create(JAXRSArchive.class);
        restArchive.addResource(MyResource.class);
        swarm.deploy(restArchive);
    }

}
