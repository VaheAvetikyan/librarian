package com.xyz.librarian.dto;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.domain.Book;

import java.util.Set;
import java.util.stream.Collectors;

public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    private Set<Long> authors;

    private BookDTO() {
    }

    public static BookDTO from(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setAuthors(book.getAuthors().stream().mapToLong(Author::getId).boxed().collect(Collectors.toSet()));
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

    public Set<Long> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Long> authors) {
        this.authors = authors;
    }
}
