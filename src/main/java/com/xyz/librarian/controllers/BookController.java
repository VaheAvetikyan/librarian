package com.xyz.librarian.controllers;

import com.xyz.librarian.domain.Book;
import com.xyz.librarian.dto.BookDTO;
import com.xyz.librarian.handlers.BookHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    BookHandler bookHandler;

    public BookController(BookHandler bookHandler) {
        this.bookHandler = bookHandler;
    }

    @GetMapping
    public List<BookDTO> getBooks() {
        return bookHandler.getBooks();
    }

    @GetMapping(params = {"page", "size"})
    public List<BookDTO> getBooks(@RequestParam("page") int page, @RequestParam("size") int size) {
        return bookHandler.getBooks(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable long id) {
        return ResponseEntity.ok(bookHandler.getBook(id));
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookHandler.addBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@RequestBody Book book, @PathVariable long id) {
        return ResponseEntity.ok(bookHandler.updateBook(book, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable long id) {
        bookHandler.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
