package org.wildfly.swarm.examples.gradle;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import static org.junit.Assert.assertNotNull;

/**
 * Example test showcasing the use of Arquillian within a Gradle project. This test case makes use of the DefaultDeployment
 * packaging (type = JAR).
 */
@RunWith(Arquillian.class)
@DefaultDeployment(type = DefaultDeployment.Type.JAR)
public class ArqDefaultDeploymentTest {

    @Inject
    private Greeter greeter;

    /**
     * Verify that we are able to execute the test within the container.
     */
    @Test
    public void testInjection() {
        assertNotNull(CDI.current());
        assertNotNull("Bean injection has failed.", greeter);
    }
}
