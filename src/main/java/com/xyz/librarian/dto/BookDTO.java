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
    private Set<AuthorSlimDTO> authors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(value = "books")
    private PublisherSlimDTO publisher;

    private BookDTO() {
    }

    public static BookDTO from(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setAuthors(book.getAuthors().stream().map(AuthorSlimDTO::from).collect(Collectors.toSet()));
        if (book.getPublisher() != null) {
            bookDTO.setPublisher(PublisherSlimDTO.from(book.getPublisher()));
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

    public PublisherSlimDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherSlimDTO publisher) {
        this.publisher = publisher;
    }

    public Set<AuthorSlimDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorSlimDTO> authors) {
        this.authors = new HashSet<>(authors);
    }
}
