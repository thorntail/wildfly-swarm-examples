package org.wildfly.swarm.it.jose;

import static org.fest.assertions.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jws.JsonWebSignature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.examples.swarm.jose.ApprovalStatus;
import org.wildfly.examples.swarm.jose.LoanRequest;
import org.wildfly.swarm.it.AbstractIntegrationTest;

public class JoseApplicationIT extends AbstractIntegrationTest {

    Client client;
    WebTarget target;

    @Before
    public void setup() throws Exception {
        client = ClientBuilder.newClient();
        client.register(new BadRequestFilter());
        target = client.target("http://localhost:8080/loans");
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    
    @Test
    public void testApprovedLoanRequest() throws Exception {
        LoanRequest request = new LoanRequest();
        request.setApprovalStatus(ApprovalStatus.APPROVED);
        
        sign(request);
        
        ApprovalStatus response = target.path("approved").request(MediaType.APPLICATION_JSON)
            .post(Entity.json(request), ApprovalStatus.class);

        assertThat(response).isSameAs(ApprovalStatus.APPROVED);
    }
    
    @Test(expected = BadRequestException.class)
    public void testRejectedApprovedLoanRequest() throws Exception {
        LoanRequest request = new LoanRequest();
        request.setApprovalStatus(ApprovalStatus.REJECTED);
        
        sign(request);
        
        target.path("mayBeApproved").request(MediaType.APPLICATION_JSON)
            .post(Entity.json(request), ApprovalStatus.class);
    }

    private void sign(LoanRequest request) throws Exception {
        final String keySet = readKeySet();
        JsonWebKeySet jwkSet = new JsonWebKeySet(keySet);
        JsonWebKey jwkKey = jwkSet.getJsonWebKeys().get(0);
        
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(request.getApprovalStatus().name());
        jws.setAlgorithmHeaderValue(jwkKey.getAlgorithm());
        jws.setKey(jwkKey.getKey());
        String signature = jws.getDetachedContentCompactSerialization();
        request.setSignature(signature);
        
        String[] parts = signature.split("\\.");
        assertThat(parts.length).isEqualTo(3);
        assertThat(parts[1].isEmpty()).isTrue();
    }

    private String readKeySet() throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try (BufferedReader is = new BufferedReader(new InputStreamReader(cl.getResourceAsStream("jwk.keys")))) {
            return is.lines().collect(Collectors.joining());
        }
    }

}
