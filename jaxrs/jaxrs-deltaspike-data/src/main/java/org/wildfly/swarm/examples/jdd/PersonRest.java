package org.wildfly.swarm.examples.jdd;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by rafaelszp on 9/8/16.
 */
@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
public class PersonRest {

    @Inject
    PersonRepository personRepository;

    @GET
    public List<Person> query(@QueryParam("name") String name, @QueryParam("document-id") String documentId){

        List<Person> persons=null;

        if(name!=null && documentId!=null){
            persons = personRepository.findByNameLikeAndDocumentId(name+'%',documentId);
        }else if(name!=null){
            persons = personRepository.findByNameLike(name+'%');
        }else if(documentId!=null){
            persons = personRepository.findByDocumentId(documentId);
        }else{
            persons = personRepository.findAll(0,10);
        }

        if(persons==null || persons.isEmpty()){
            WebApplicationException ex = new WebApplicationException(404); // NOT FOUND
            throw ex;
        }

        return persons;

    }

    @GET
    @Path("/{id: \\d+}")
    public Response getById(@PathParam("id") Long id){
        Person person = personRepository.findBy(id);
        if(person==null){
            WebApplicationException ex = new WebApplicationException(404); // NOT FOUND
            throw ex;
        }
        return Response.status(Response.Status.OK).entity(person).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Person person){
        try{
            personRepository.save(person);
            return Response.status(Response.Status.CREATED).build();
        }catch (Exception e){
            WebApplicationException ex = new WebApplicationException(e.getMessage(),500); //Internal Server Error
            throw ex;
        }
    }
    @PUT
    @Path("/{id: \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Person person){
        try{
            Person found = personRepository.findBy(id);
            if(found==null){
                throw new Exception("PERSON NOT FOUND. CANNOT UPDATE!");
            }
            personRepository.save(person);
            return Response.status(Response.Status.OK).build();
        }catch (Exception e){
            WebApplicationException ex = new WebApplicationException(e.getMessage(),500); //Internal Server Error
            throw ex;
        }
    }

}
