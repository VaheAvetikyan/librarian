package com.xyz.librarian.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.librarian.domain.Author;
import com.xyz.librarian.domain.Book;

import java.util.HashSet;
import java.util.Set;

public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    @JsonIgnoreProperties(value = "books")
    private Set<Author> authors;

    private BookDTO() {
    }

    public static BookDTO from(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setAuthors(book.getAuthors());
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

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = new HashSet<>(authors);
    }
}
