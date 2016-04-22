package org.wildfly.swarm.examples.camel.cxf.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "GreetingService", targetNamespace="http://wildfly.swarm.examples.camel.cxf.jaxws")
public interface GreetingService {
    @WebMethod(operationName = "greet", action = "urn:greet")
    String greet(@WebParam(name = "name", targetNamespace="http://wildfly.swarm.examples.camel.cxf.jaxws") String name);
}
