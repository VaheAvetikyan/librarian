package com.xyz.librarian.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xyz.librarian.domain.Book;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    @JsonIgnoreProperties(value = "books")
    private Set<AuthorDTO> authors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(value = "books")
    private PublisherDTO publisher;

    private BookDTO() {
    }

    public static BookDTO from(Book book) {
        BookDTO bookDTO = slimObject(book);
        bookDTO.setAuthors(book.getAuthors().stream().map(AuthorDTO::slimObject).collect(Collectors.toSet()));
        return bookDTO;
    }

    static BookDTO slimObject(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIsbn(book.getIsbn());
        if (book.getPublisher() != null) {
            bookDTO.setPublisher(PublisherDTO.slimObject(book.getPublisher()));
        }
        return bookDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public PublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherDTO publisher) {
        this.publisher = publisher;
    }

    public Set<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorDTO> authors) {
        this.authors = new HashSet<>(authors);
    }
}
