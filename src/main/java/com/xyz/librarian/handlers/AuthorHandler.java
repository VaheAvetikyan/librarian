package com.xyz.librarian.handlers;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.dto.AuthorDTO;
import com.xyz.librarian.services.AuthorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorHandler {
    private final Logger LOGGER = LogManager.getLogger(AuthorHandler.class);

    private final AuthorService authorService;

    public AuthorHandler(AuthorService authorService) {
        this.authorService = authorService;
    }

    public List<AuthorDTO> getAuthors() {
        Iterable<Author> authors = authorService.getAuthors();
        List<AuthorDTO> authorDtoList = new ArrayList<>();
        authors.forEach(author -> authorDtoList.add(AuthorDTO.from(author)));
        authorDtoList.sort(Comparator.comparing(AuthorDTO::getLastName));
        LOGGER.info("Authors retrieved [{}]",
                authorDtoList.stream()
                        .map(author -> author.getFirstName() + " " + author.getLastName())
                        .collect(Collectors.joining(", ")));
        return authorDtoList;
    }

    public AuthorDTO getAuthorByID(long id) {
        Author author = authorService.getAuthorByID(id);
        return AuthorDTO.from(author);
    }

    public AuthorDTO addAuthor(Author author) {
        Author created = authorService.addAuthor(author);
        return AuthorDTO.from(created);
    }

    public AuthorDTO updateAuthor(Author author, long id) {
        try {
            authorService.getAuthorByID(id);
        } catch (RuntimeException e) {
            LOGGER.debug("Author with id {} does not exist", id);
            throw e;
        }
        author.setId(id);
        Author updated = authorService.updateAuthor(author);
        return AuthorDTO.from(updated);
    }

    public void deleteAuthor(@PathVariable long id) {
        Author author = authorService.getAuthorByID(id);
        if (author == null) {
            return;
        }
        authorService.removeAuthor(author);
    }
}
