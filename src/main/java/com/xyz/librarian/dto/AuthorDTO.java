package com.xyz.librarian.dto;

import com.xyz.librarian.domain.Author;

import java.util.Set;
import java.util.stream.Collectors;

public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<BookRecord> bookRecords;

    private AuthorDTO() {
    }

    public static AuthorDTO from(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());
        authorDTO.setBooks(author.getBooks()
                .stream()
                .map(book -> new BookRecord(book.getId(), book.getTitle(), book.getIsbn()))
                .collect(Collectors.toSet()));
        return authorDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<BookRecord> getBooks() {
        return bookRecords;
    }

    private record BookRecord(Long id, String title, String isbn) {
    }

    public void setBooks(Set<BookRecord> bookRecords) {
        this.bookRecords = bookRecords;
    }
}
