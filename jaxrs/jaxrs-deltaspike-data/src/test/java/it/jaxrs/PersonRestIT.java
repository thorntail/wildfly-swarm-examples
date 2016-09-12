package it.jaxrs;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.examples.jdd.Person;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by rafaelszp on 9/8/16.
 */

@RunWith(Arquillian.class)
public class PersonRestIT  extends AbstractIntegrationTest{

    public static final String API_URL = "http://127.0.0.1:8080/api/persons";
    Client client = ClientBuilder.newBuilder().build();


    WebTarget target;

    @Before
    public void before(){
        target = client.target(API_URL);
    }

//    If you want to make you tests work with you IDE, just uncomment the following lines
    /*@Deployment
    public static Archive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create( JAXRSArchive.class );
        deployment.addPackage(Person.class.getPackage());
        deployment.addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"));
        deployment.addAsWebInfResource(new File("src/main/resources/import.sql"),"classes/import.sql");
        deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml", PersonRestIT.class.getClassLoader()), "classes/META-INF/persistence.xml");
        deployment.addAsWebInfResource(new File("src/main/webapp/WEB-INF/jaxrs-deltaspike-data-ds.xml"));
        deployment.addAllDependencies();
        System.out.println(deployment.toString(true));
        return deployment;
    }*/

    @Test
    @RunAsClient
    public void shouldGetAll(){
        Response response = target.request().get();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    @RunAsClient
    public void shouldCreateANewPerson(){
        Person person = new Person();
        person.setDocumentId("a607feec176eb4b4fc32d7ca69f8e343");
        person.setName("MARIO DO ARMARIO");
        Response response = target.request().post(Entity.entity(person, "application/json"));
        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
        assertThat(response.getHeaderString("Location")).startsWith("http://127.0.0.1:8080/api/persons/");
        response.close();
        Response getPerson = client.target(response.getHeaderString("Location")).request().get();
        Person foundPerson = getPerson.readEntity(Person.class);
        getPerson.close();
        assertThat(foundPerson.getDocumentId()).isEqualTo(person.getDocumentId());
    }

    @Test
    @RunAsClient
    public void shouldPut(){
        target = client.target(API_URL+"/1");
        Person person = new Person();
        person.setId(1L);
        person.setDocumentId("66677fb9980dcc0f996c91e08ef6d6de");
        person.setName("ELIS SABELLA");
        Response response = target.request().put(Entity.entity(person, "application/json"));
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    @RunAsClient
    public void shouldGetByName(){
        target = client.target(API_URL+"/?name=J");
        Response response = target.request().get();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    @RunAsClient
    public void shouldGetByNameAndDocumentID(){
        target = client.target(API_URL+"/?name=P&document-id=9e32f1112a8d64e75fea11c65c99f2ad");
        Response response = target.request().get();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    @RunAsClient
    public void shouldGetByDocumentID(){
        target = client.target(API_URL+"/?document-id=9e32f1112a8d64e75fea11c65c99f2ad");
        Response response = target.request().get();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

}
