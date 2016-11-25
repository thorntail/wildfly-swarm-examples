package org.wildfly.swarm.examples.jaxrs.cdi.beanvalidation;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/books")
public class BooksResource {
    @Inject
    private BooksService books;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAll() {
        return books.list();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Book create(@QueryParam("isbn") String isbn,
                       @QueryParam("title") String title,
                       @QueryParam("author") String author) {
        return books.create(isbn, title, author);
    }
}
