package org.wildfly.swarm.examples.opentracing.jaxrs;

import org.hawkular.apm.client.opentracing.APMTracer;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.opentracing.jaxrs.OpenTracingFraction;

import io.opentracing.Tracer;
import sk.loffay.opentracing.jax.rs.server.ServerTracingDynamicFeature;

/**
 * @author Pavol Loffay
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("\n\n\nApp Main\n\n\n");

        Swarm container = new Swarm();
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackage(Main.class.getPackage());
        deployment.addAllDependencies();

        ServerTracingDynamicFeature.Builder builder = ServerTracingDynamicFeature.Builder.traceAll(new APMTracer());

        container.fraction(new OpenTracingFraction(builder));
        container.start().deploy(deployment);
    }
}
