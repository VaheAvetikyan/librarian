package com.xyz.librarian.controllers;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.dto.AuthorDTO;
import com.xyz.librarian.services.AuthorService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping("/authors")
    public List<AuthorDTO> getAuthors() {
        Iterable<Author> authors = authorService.getAuthors();
        List<AuthorDTO> authorDtoList = new ArrayList<>();
        authors.forEach(it -> authorDtoList.add(authorService.convertToDto(it)));
        return authorDtoList;
    }

    @RequestMapping("/authors/{id}")
    public AuthorDTO getAuthor(@PathVariable Long id) {
        Author author = authorService.getAuthorByID(id);
        return authorService.convertToDto(author);
    }
}
