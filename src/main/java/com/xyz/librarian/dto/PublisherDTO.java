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
    private Set<BookSlimDTO> books = new HashSet<>();

    private PublisherDTO() {
    }

    public static PublisherDTO from(Publisher publisher) {
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setId(publisher.getId());
        publisherDTO.setName(publisher.getName());
        publisherDTO.setAddress(AddressDTO.from(publisher.getAddress()));
        publisherDTO.setBooks(publisher.getBooks().stream().map(BookSlimDTO::from).collect(Collectors.toSet()));
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

    public Set<BookSlimDTO> getBooks() {
        return books;
    }

    public void setBooks(Set<BookSlimDTO> books) {
        this.books = books;
    }
}
