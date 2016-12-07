package org.wildfly.swarm.examples.jpajaxrscdi;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Ken Finnigan
 */
@ApplicationPath("/")
public class MyApplication extends Application {

    public MyApplication() {
    }
}
