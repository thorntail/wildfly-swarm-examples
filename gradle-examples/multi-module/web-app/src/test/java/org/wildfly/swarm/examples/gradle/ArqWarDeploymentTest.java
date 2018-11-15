package org.wildfly.swarm.examples.gradle;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.undertow.WARArchive;

import static org.junit.Assert.assertTrue;

/**
 * Example test showcasing the use of Arquillian within a Gradle project. This test case constructs a custom web archive.
 */
@RunWith(Arquillian.class)
public class ArqWarDeploymentTest {

    @ArquillianResource
    private URL url;

    /**
     * Build the archive for testing.
     */
    @Deployment
    public static Archive createDeployment() throws Exception {
        WARArchive archive = ShrinkWrap.create(WARArchive.class, "SampleTest.war");
        archive.addClass(Greeter.class);
        archive.addClass(HelloRest.class);
        archive.addClass(MyApplication.class);
        archive.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        // Include the test case related dependencies (non-fractions) explicitly.
        // Do not use the addAllDependencies() as that will pull in dependencies that won't be satisfied in a test case.
        // If you ended up on https://issues.jboss.org/browse/THORN-1072 via a google search, then make sure that you remove
        // the addAllDependencies() invocation.
        archive.addDependency("commons-io:commons-io:2.4");
        return archive;
    }

    /**
     * Verify that we are able to make use of the RunAsClient annotation as well.
     */
    @Test
    @RunAsClient
    public void testAPIInvocation() throws IOException {
        URL endPoint = new URL(url.getProtocol(), url.getHost(), url.getPort(), "/api");
        String content = IOUtils.toString(endPoint, Charset.forName("UTF-8").toString());
        assertTrue("Did not receive the appropriate content.", content.contains("hello: Thorntail + gradle + java"));
    }

}
