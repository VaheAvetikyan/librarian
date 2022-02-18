package com.xyz.librarian.dto;

import com.xyz.librarian.domain.Author;

public class AuthorSlimDTO {
    private Long id;
    private String firstName;
    private String lastName;

    private AuthorSlimDTO() {
    }

    public static AuthorSlimDTO from(Author author) {
        AuthorSlimDTO authorSlimDTO = new AuthorSlimDTO();
        authorSlimDTO.setId(author.getId());
        authorSlimDTO.setFirstName(author.getFirstName());
        authorSlimDTO.setLastName(author.getLastName());
        return authorSlimDTO;
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
}
