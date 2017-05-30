package org.wildfly.swarm.examples.rar.deployment;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.it.AbstractIntegrationTest;
import org.wildfly.swarm.it.Log;

/**
 * @author Ralf Battenfeld
 */
@RunWith(Arquillian.class)
public class ResourceAdapterIT extends AbstractIntegrationTest {

    @Test
    public void testIt() throws Exception {
        Log log = getStdOutLog();
        assertThatLog( log ).hasLineContaining( "WFLYJCA0002: Bound JCA ConnectionFactory [java:global/XADiskCF]" );
        assertThatLog( log ).hasLineContaining( "WFLYSRV0010: Deployed \"xadisk.rar\"" );
        assertThatLog( log ).hasLineContaining( "java:module/FileIOBean");
    }

}
