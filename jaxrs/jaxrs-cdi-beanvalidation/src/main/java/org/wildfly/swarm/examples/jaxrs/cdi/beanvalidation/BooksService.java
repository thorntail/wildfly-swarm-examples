package org.wildfly.swarm.examples.jaxrs.cdi.beanvalidation;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApplicationScoped
public class BooksService {
    private AtomicLong idGenerator = new AtomicLong();

    private List<Book> books = new CopyOnWriteArrayList<>();

    public List<Book> list() {
        return books;
    }

    @Valid
    public Book create(
            @NotNull(message = "ISBN must be set")
            @Pattern(regexp = "^\\d{9}[\\d|X]$", message = "ISBN must be valid") // the regexp here is very simplistic
            String isbn,
            @NotNull(message = "Title must be set")
            String title,
            @NotNull(message = "Author must be set")
            String author
    ) {
        Book book = new Book(idGenerator.incrementAndGet(), isbn, title, author);
        books.add(book);
        return book;
    }
}
