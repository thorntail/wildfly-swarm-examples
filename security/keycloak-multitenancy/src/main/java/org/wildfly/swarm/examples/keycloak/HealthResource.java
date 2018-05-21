package org.wildfly.swarm.examples.keycloak;

import java.io.IOException;
import java.net.InetAddress;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.wildfly.swarm.SwarmInfo;

@Path("health")
@Produces("application/json")
public class HealthResource {

    @Context
    private HttpHeaders httpHeaders;
    
    @GET
    public String getAll() {
        ModelNode node = new ModelNode();
        addRealmProperty(node);
        node.add("node", getNodeInfo());
        node.add("threads", getThreadsInfo());
        node.add("heap", getHeapInfo());
        
        return node.toJSONString(false);
    }
    
    @GET
    @Path("node")
    public String getNode() {
        return addRealmProperty(getNodeInfo()).toJSONString(false);
    }
    @GET
    @Path("heap")
    public String getHeap() {
        return addRealmProperty(getHeapInfo()).toJSONString(false);
    }
    @GET
    @Path("threads")
    public String getThreads() {
        return addRealmProperty(getThreadsInfo()).toJSONString(false);
    }
    
    private ModelNode getNodeInfo() {

        ModelNode op = new ModelNode();
        op.get("address").setEmptyList();
        op.get("operation").set("query");
        op.get("select").add("name");
        op.get("select").add("server-state");
        op.get("select").add("suspend-state");
        op.get("select").add("running-mode");
        op.get("select").add("uuid");

        return getModelNodeResponse(op);

    }

    private ModelNode getHeapInfo() {
        ModelNode op = new ModelNode();
        op.get("address").add("core-service", "platform-mbean");
        op.get("address").add("type", "memory");
        op.get("operation").set("query");
        op.get("select").add("heap-memory-usage");
        op.get("select").add("non-heap-memory-usage");

        return getModelNodeResponse(op);
    }

    private ModelNode getThreadsInfo() {
        ModelNode op = new ModelNode();
        op.get("address").add("core-service", "platform-mbean");
        op.get("address").add("type", "threading");
        op.get("operation").set("query");
        op.get("select").add("thread-count");
        op.get("select").add("peak-thread-count");
        op.get("select").add("total-started-thread-count");
        op.get("select").add("current-thread-cpu-time");
        op.get("select").add("current-thread-user-time");

        return getModelNodeResponse(op);
    }
    
    private static ModelNode unwrap(ModelNode response) {
        if (response.get("outcome").asString().equals("success")) {
            return response.get("result");
        } else {
            return response;
        }
    }

    private ModelNode addRealmProperty(ModelNode node) {
        return node.add("realm", httpHeaders.getHeaderString("Realm"));
    }
    
    private ModelNode getModelNodeResponse(ModelNode op) {
        try (ModelControllerClient client = ModelControllerClient.Factory.create(
                InetAddress.getByName("localhost"), 9990)) {
            ModelNode response = client.execute(op);
            ModelNode unwrapped = unwrap(response);
            unwrapped.get("swarm-version").set(SwarmInfo.VERSION);
            return unwrapped;
        } catch (IOException e) {
            return new ModelNode().get("failure-description").set(e.getMessage());
        }
    }
}
