package org.wildfly.swarm.example.docker;

import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.config.logging.Level;
import org.wildfly.swarm.logging.LoggingFraction;

//@Slf4j
public class SwarmMain {
    public static void main(String[] args) throws Exception {
        Swarm swarm = new Swarm();

        Level logLevel = Level.INFO;
        swarm.fraction(new LoggingFraction()
                               .formatter("PATTERN", "%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n")
                               .consoleHandler(logLevel, "PATTERN")
                               .rootLogger(logLevel, "CONSOLE"));
        swarm.start().deploy();
    }
}
