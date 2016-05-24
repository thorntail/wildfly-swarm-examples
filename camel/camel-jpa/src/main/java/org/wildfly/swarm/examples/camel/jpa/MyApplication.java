package org.wildfly.swarm.examples.camel.jpa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.wildfly.swarm.examples.camel.jpa.model.Customer;

@ApplicationPath("/example-camel-jpa")
public class MyApplication extends Application {

    public MyApplication() throws IOException {
    	try (InputStream input = Customer.class.getResourceAsStream("customer-john.xml")) {
        	Path destination = new File(System.getProperty("jboss.server.data.dir")).toPath().resolve("customers");
        	destination.toFile().mkdirs();
    		Files.copy(input, destination.resolve("customer.xml"));
    	}
    }
}
