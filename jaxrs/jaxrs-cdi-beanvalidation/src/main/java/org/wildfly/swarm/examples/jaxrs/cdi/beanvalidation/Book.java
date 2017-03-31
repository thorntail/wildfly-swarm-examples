package org.wildfly.swarm.examples.jaxrs.cdi.beanvalidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Book {
    private long id;

    @NotNull(message = "ISBN must be set")
    @Pattern(regexp = "^\\d{9}[\\d|X]$", message = "ISBN must be valid") // the regexp here is very simplistic
    private String isbn;

    @NotNull(message = "Title must be set")
    private String title;

    @NotNull(message = "Author must be set")
    private String author;

    public Book(long id, String isbn, String title, String author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
