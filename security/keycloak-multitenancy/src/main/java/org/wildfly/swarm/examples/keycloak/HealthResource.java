package org.wildfly.swarm.examples.keycloak;

import java.io.IOException;
import java.net.InetAddress;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.wildfly.swarm.SwarmInfo;

@Path("health")
@Produces("application/json")
public class HealthResource {

    @GET
    public String getAll() {
        ModelNode node = new ModelNode();
        node.add("node", getNodeInfo());
        node.add("threads", getThreadsInfo());
        node.add("heap", getHeapInfo());
        return node.toJSONString(false);
    }
    
    @GET
    @Path("node")
    public String getNode() {
        return getNodeInfo().toJSONString(false);
    }
    @GET
    @Path("heap")
    public String getHeap() {
        return getHeapInfo().toJSONString(false);
    }
    @GET
    @Path("threads")
    public String getThreads() {
        return getThreadsInfo().toJSONString(false);
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

    private ModelNode getHeapInfo() {
        ModelNode op = new ModelNode();
        op.get("address").add("core-service", "platform-mbean");
        op.get("address").add("type", "memory");
        op.get("operation").set("query");
        op.get("select").add("heap-memory-usage");
        op.get("select").add("non-heap-memory-usage");

        try (ModelControllerClient client = ModelControllerClient.Factory.create(
                InetAddress.getByName("localhost"), 9990)) {
            ModelNode response = client.execute(op);
            return unwrap(response);
        } catch (IOException e) {
            return new ModelNode().get("failure-description").set(e.getMessage());
        }
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

        try (ModelControllerClient client = ModelControllerClient.Factory.create(
                InetAddress.getByName("localhost"), 9990)) {
            ModelNode response = client.execute(op);
            return unwrap(response);
        } catch (IOException e) {
            return new ModelNode().get("failure-description").set(e.getMessage());
        }
    }
    
    private static ModelNode unwrap(ModelNode response) {
        if (response.get("outcome").asString().equals("success")) {
            return response.get("result");
        } else {
            return response;
        }
    }

}
