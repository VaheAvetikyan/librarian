package com.xyz.librarian.controllers;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.domain.Book;
import com.xyz.librarian.services.AuthorService;
import com.xyz.librarian.services.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
    private final Logger LOGGER = LogManager.getLogger(BookController.class);

    private final AuthorService authorService;
    private final BookService bookService;

    public BookController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Book>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Iterable<Book>> getBooks(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(bookService.getBooks(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable long id) {
        return ResponseEntity.ok(bookService.getBookByID(id));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        if (book.getAuthors() != null) {
            assignAuthorsToBook(book);
        }
        Book created = bookService.addBook(book);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable long id) {
        try {
            bookService.getBookByID(id);
        } catch (RuntimeException e) {
            LOGGER.debug("Book with id {} does not exist", id);
            throw e;
        }
        book.setId(id);
        if (book.getAuthors() != null) {
            assignAuthorsToBook(book);
        }
        Book updated = bookService.updateBook(book);
        return ResponseEntity.ok(updated);
    }

    private void assignAuthorsToBook(Book book) {
        authorService.getAuthorsByID(book.getAuthors()
                        .stream()
                        .map(Author::getId)
                        .collect(Collectors.toSet()))
                .forEach(author -> author.addToBooks(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable long id) {
        Book book = bookService.getBookByID(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        bookService.removeBook(book);
        return ResponseEntity.noContent().build();
    }
}
