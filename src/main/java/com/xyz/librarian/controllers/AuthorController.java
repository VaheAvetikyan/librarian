package com.xyz.librarian.controllers;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.dto.AuthorDTO;
import com.xyz.librarian.handlers.AuthorHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorHandler authorHandler;

    public AuthorController(AuthorHandler authorHandler) {
        this.authorHandler = authorHandler;
    }

    @GetMapping
    public List<AuthorDTO> getAuthors() {
        return authorHandler.getAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable long id) {
        return ResponseEntity.ok(authorHandler.getAuthorByID(id));
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(authorHandler.addAuthor(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@RequestBody Author author, @PathVariable long id) {
        return ResponseEntity.ok(authorHandler.updateAuthor(author, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable long id) {
        authorHandler.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
