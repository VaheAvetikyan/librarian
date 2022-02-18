package com.xyz.librarian.dto;

import com.xyz.librarian.domain.Publisher;

public class PublisherSlimDTO {
    private Long id;
    private String name;
    private AddressDTO address;

    private PublisherSlimDTO() {
    }

    public static PublisherSlimDTO from(Publisher publisher) {
        PublisherSlimDTO publisherSlimDTO = new PublisherSlimDTO();
        publisherSlimDTO.setId(publisher.getId());
        publisherSlimDTO.setName(publisher.getName());
        publisherSlimDTO.setAddress(AddressDTO.from(publisher.getAddress()));
        return publisherSlimDTO;
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
}
