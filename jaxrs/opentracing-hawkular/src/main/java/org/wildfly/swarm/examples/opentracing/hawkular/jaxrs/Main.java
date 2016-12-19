package org.wildfly.swarm.examples.opentracing.hawkular.jaxrs;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.opentracing.hawkular.jaxrs.OpenTracingHawkularFraction;

/**
 * @author Pavol Loffay
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Swarm container = new Swarm(args);

        OpenTracingHawkularFraction openTracingHawkularFraction = new OpenTracingHawkularFraction();

        openTracingHawkularFraction.apmJaxRsTracingBuilder()
                .withServiceName("wildfly-swarm")
//                .withConsoleRecorder(true)
                .withBatchRecorderBuilder(new OpenTracingHawkularFraction.TraceRecorderBuilder()
                    .withHttpRecorder("jdoe", "password", "http://localhost:8180"))
                .withSampleRate(100);

        container.fraction(openTracingHawkularFraction);

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackage( Main.class.getPackage() );
        deployment.addAllDependencies();

        container.start().deploy(deployment);
    }
}
