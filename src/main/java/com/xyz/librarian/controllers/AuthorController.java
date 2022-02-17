package com.xyz.librarian.controllers;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.domain.Book;
import com.xyz.librarian.dto.AuthorDTO;
import com.xyz.librarian.services.AuthorService;
import com.xyz.librarian.services.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final Logger LOGGER = LogManager.getLogger(BookController.class);

    private final AuthorService authorService;
    public final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
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

    @GetMapping("/{id}")
    public AuthorDTO getAuthor(@PathVariable long id) {
        Author author = authorService.getAuthorByID(id);
        return AuthorDTO.from(author);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody Author author) {
        if (author.getBooks() != null) {
            assignBooksToAuthor(author);
        }
        Author created = authorService.addAuthor(author);
        return ResponseEntity.ok(AuthorDTO.from(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@RequestBody Author author, @PathVariable long id) {
        try {
            authorService.getAuthorByID(id);
        } catch (RuntimeException e) {
            LOGGER.debug("Author with id {} does not exist", id);
            throw e;
        }
        author.setId(id);
        if (author.getBooks() != null) {
            assignBooksToAuthor(author);
        }
        Author updated = authorService.updateAuthor(author);
        return ResponseEntity.ok(AuthorDTO.from(updated));
    }

    private void assignBooksToAuthor(Author author) {
        bookService.getBooksByID(author.getBooks()
                        .stream()
                        .map(Book::getId)
                        .collect(Collectors.toSet()))
                .forEach(book -> book.addToAuthors(author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable long id) {
        Author author = authorService.getAuthorByID(id);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        authorService.removeAuthor(author);
        return ResponseEntity.noContent().build();
    }
}
