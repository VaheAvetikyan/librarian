package com.xyz.librarian.dto;

import com.xyz.librarian.domain.Book;

import java.util.Set;
import java.util.stream.Collectors;

public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    private Set<AuthorRecord> authors;

    private BookDTO() {
    }

    public static BookDTO from(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setAuthors(book.getAuthors()
                .stream()
                .map(author -> new AuthorRecord(author.getId(), author.getFirstName(), author.getLastName()))
                .collect(Collectors.toSet()));
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

    public Set<AuthorRecord> getAuthors() {
        return authors;
    }

    private record AuthorRecord(Long id, String firstName, String lastName) {
    }

    public void setAuthors(Set<AuthorRecord> authors) {
        this.authors = authors;
    }
}
