package org.wildfly.examples.swarm.jose;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.wildfly.swarm.jose.Jose;

@Path("/loans")
@ApplicationScoped
public class BankResource {
    @Inject 
    private Jose jose;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/approved")
    public ApprovalStatus approved(LoanRequest request) {
        return getApprovalStatus(request);
    }
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/mayBeApproved")
    public ApprovalStatus mayBeApproved(LoanRequest request) {
        return getApprovalStatus(request);
    }
    
    private ApprovalStatus getApprovalStatus(LoanRequest request) {
        ApprovalStatus status = request.getApprovalStatus();
        jose.verifyDetached(request.getSignature(), status.name());
        return status;
    }
}
