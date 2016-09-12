package org.wildfly.swarm.examples.jdd;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by rafaelszp on 9/8/16.
 */
@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Transactional
public class PersonRest {

    @Inject
    private PersonRepository personRepository;

    @GET
    public List<Person> query(@QueryParam("name") String name, @QueryParam("document-id") String documentId){

        Stream<Person> persons;

        if(name!=null && documentId!=null){
            persons = personRepository.findByNameLikeAndDocumentId(name+'%',documentId);
        }else if(name!=null){
            persons = personRepository.findByNameLike(name+'%');
        }else if(documentId!=null){
            persons = personRepository.findByDocumentId(documentId);
        }else{
            persons = personRepository.findTop10OrderByName();
        }

        return persons.collect(toList());

    }

    @GET
    @Path("/{id: \\d+}")
    public Response getById(@PathParam("id") Long id){
        Person person = personRepository.findBy(id).orElseThrow(() -> new WebApplicationException(404));
        return Response.status(Response.Status.OK).entity(person).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Person person, @Context UriInfo uriInfo){
        try{
            Person savedPerson = personRepository.save(person);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Long.toString(savedPerson.getId()));
            return Response.created(builder.build()).build();
        }catch (Exception e){
            throw new WebApplicationException(e.getMessage(),500);
        }
    }
    @PUT
    @Path("/{id: \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Person person){
        try{
            personRepository.findBy(id).orElseThrow(() -> new WebApplicationException("Person not found, cannot update.", 404));
            personRepository.save(person);
            return Response.status(Response.Status.OK).build();
        }catch (Exception e){
            throw new WebApplicationException(e.getMessage(),500);
        }
    }

}
