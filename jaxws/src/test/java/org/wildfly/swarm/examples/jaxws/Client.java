package org.wildfly.swarm.examples.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * A Client stub to the HelloWorld JAX-WS Web Service.
 *
 * @author lnewson@redhat.com
 * @author Yoshimasa Tanabe
 *
 */
public class Client implements HelloWorldService {

    private HelloWorldService helloWorldService;

    /**
     * Default constructor
     *
     * @param url The URL to the Hello World WSDL endpoint.
     */
    public Client(final URL wsdlUrl) {
        QName serviceName = new QName("http://wildfly-swarm.io/HelloWorld", "HelloWorldService");

        Service service = Service.create(wsdlUrl, serviceName);
        helloWorldService = service.getPort(HelloWorldService.class);
        assert (helloWorldService != null);
    }

    /**
     * Default constructor
     *
     * @param url The URL to the Hello World WSDL endpoint.
     * @throws MalformedURLException if the WSDL url is malformed.
     */
    public Client(final String url) throws MalformedURLException {
        this(new URL(url));
    }

    /**
     * Gets the Web Service to say hello
     */
    @Override
    public String sayHello() {
        return helloWorldService.sayHello();
    }

    /**
     * Gets the Web Service to say hello to someone
     */
    @Override
    public String sayHelloToName(final String name) {
        return helloWorldService.sayHelloToName(name);
    }

    /**
     * Gets the Web Service to say hello to a group of people
     */
    @Override
    public String sayHelloToNames(final List<String> names) {
        return helloWorldService.sayHelloToNames(names);
    }

}
