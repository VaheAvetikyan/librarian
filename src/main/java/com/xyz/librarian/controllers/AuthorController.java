package com.xyz.librarian.controllers;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.dto.AuthorDTO;
import com.xyz.librarian.dto.BookDTO;
import com.xyz.librarian.services.AuthorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private final Logger LOGGER = LogManager.getLogger(BookController.class);

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping("/authors")
    public List<AuthorDTO> getAuthors() {
        Iterable<Author> authors = authorService.getAuthors();
        List<AuthorDTO> authorDtoList = new ArrayList<>();
        authors.forEach(author -> authorDtoList.add(authorService.convertToDto(author)));
        authorDtoList.sort(Comparator.comparing(AuthorDTO::getLastName));
        LOGGER.info("Authors retrieved [{}]",
                authorDtoList.stream()
                        .map(author -> author.getFirstName() + " " + author.getLastName())
                        .collect(Collectors.joining(", ")));
        return authorDtoList;
    }

    @RequestMapping("/authors/{id}")
    public AuthorDTO getAuthor(@PathVariable Long id) {
        Author author = authorService.getAuthorByID(id);
        return authorService.convertToDto(author);
    }
}
