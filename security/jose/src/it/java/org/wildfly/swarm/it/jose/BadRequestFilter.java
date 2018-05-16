package org.wildfly.swarm.it.jose;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;

import org.wildfly.examples.swarm.jose.ApprovalStatus;
import org.wildfly.examples.swarm.jose.LoanRequest;

@Provider
public class BadRequestFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        LoanRequest request = (LoanRequest)requestContext.getEntity();
        if (request.getApprovalStatus() == ApprovalStatus.REJECTED) {
            request.setApprovalStatus(ApprovalStatus.APPROVED);
        }
    }

}
