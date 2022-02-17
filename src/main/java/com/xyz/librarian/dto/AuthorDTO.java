package com.xyz.librarian.dto;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.domain.Book;

import java.util.Set;
import java.util.stream.Collectors;

public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<Long> books;

    private AuthorDTO() {
    }

    public static AuthorDTO from(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());
        authorDTO.setBooks(author.getBooks().stream().mapToLong(Book::getId).boxed().collect(Collectors.toSet()));
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

    public Set<Long> getBooks() {
        return books;
    }

    public void setBooks(Set<Long> books) {
        this.books = books;
    }
}
