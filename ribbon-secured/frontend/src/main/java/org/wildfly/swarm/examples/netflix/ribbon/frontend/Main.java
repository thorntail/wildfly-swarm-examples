package org.wildfly.swarm.examples.netflix.ribbon.frontend;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jgroups.JGroupsFraction;
import org.wildfly.swarm.spi.api.SocketBinding;
import org.wildfly.swarm.undertow.WARArchive;

/**
 * @author Lance Ball
 */
public class Main {
    public static void main(String... args) throws Exception {
        Swarm container = new Swarm();
        SocketBinding customJGroupsTcpBinding = new SocketBinding("jgroups-custom-tcp");
        customJGroupsTcpBinding.port(9090);
        container.socketBinding("standard-sockets", customJGroupsTcpBinding);
        JGroupsFraction fraction = new JGroupsFraction()
                .defaultChannel("swarm-jgroups")
                .stack("tcpping", (s) -> {
                    s.transport("TCP", (t) -> {
                        t.socketBinding("jgroups-custom-tcp");
                    });
                    s.protocol("TCPPING", (p) -> {
                        p.property("initial_hosts", "localhost[9090]")
                                .property("port_range", "3");
                    });
                    s.protocol("FD_SOCK", (p) -> {
                        p.socketBinding("jgroups-tcp-fd");
                    });
                    s.protocol("FD_ALL");
                    s.protocol("VERIFY_SUSPECT");
                    s.protocol("pbcast.NAKACK2");
                    s.protocol("UNICAST3");
                    s.protocol("pbcast.STABLE");
                    s.protocol("pbcast.GMS", (p) -> {
                        p.property("join_timeout", "3000");
                    });
                    s.protocol("MFC");
                    s.protocol("FRAG2");
                    s.protocol("RSVP");
                })
                .channel("swarm-jgroups", (c) -> {
                    c.stack("tcpping");
                });
        container.fraction(fraction);
        container.start();
        WARArchive war = ShrinkWrap.create(WARArchive.class, "frontend.war");
        war.staticContent();
        war.addAllDependencies();
        container.deploy(war);
    }
}