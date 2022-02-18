package com.xyz.librarian.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xyz.librarian.domain.Book;

public class BookSlimDTO {
    private Long id;
    private String title;
    private String isbn;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(value = "books")
    private PublisherSlimDTO publisher;

    private BookSlimDTO() {
    }

    public static BookSlimDTO from(Book book) {
        BookSlimDTO bookSlimDTO = new BookSlimDTO();
        bookSlimDTO.setId(book.getId());
        bookSlimDTO.setTitle(book.getTitle());
        bookSlimDTO.setIsbn(book.getIsbn());
        if (book.getPublisher() != null) {
            bookSlimDTO.setPublisher(PublisherSlimDTO.from(book.getPublisher()));
        }
        return bookSlimDTO;
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
}
