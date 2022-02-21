package com.xyz.librarian.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyz.librarian.domain.Publisher;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PublisherDTO {
    private Long id;
    private String name;
    private AddressDTO address;

    @JsonIgnoreProperties(value = "publisher")
    private Set<BookDTO> books = new HashSet<>();

    private PublisherDTO() {
    }

    public static PublisherDTO from(Publisher publisher) {
        PublisherDTO publisherDTO = slimObject(publisher);
        publisherDTO.setBooks(publisher.getBooks().stream().map(BookDTO::slimObject).collect(Collectors.toSet()));
        return publisherDTO;
    }

    static PublisherDTO slimObject(Publisher publisher) {
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setId(publisher.getId());
        publisherDTO.setName(publisher.getName());
        publisherDTO.setAddress(AddressDTO.from(publisher.getAddress()));
        return publisherDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Set<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(Set<BookDTO> books) {
        this.books = books;
    }
}
