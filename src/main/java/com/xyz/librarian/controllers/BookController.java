package com.xyz.librarian.controllers;

import com.xyz.librarian.domain.Book;
import com.xyz.librarian.dto.BookDTO;
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
@RequestMapping("/books")
public class BookController {
    private final Logger LOGGER = LogManager.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getBooks() {
        Iterable<Book> books = bookService.getBooks();
        List<BookDTO> bookDTOList = new ArrayList<>();
        books.forEach(book -> bookDTOList.add(BookDTO.from(book)));
        bookDTOList.sort(Comparator.comparing(BookDTO::getTitle));
        LOGGER.debug("Books retrieved [{}]",
                bookDTOList.stream()
                        .map(BookDTO::getTitle)
                        .collect(Collectors.joining(", ")));
        return bookDTOList;
    }

    @GetMapping(params = {"page", "size"})
    public List<BookDTO> getBooks(@RequestParam("page") int page, @RequestParam("size") int size) {
        Iterable<Book> books = bookService.getBooks(page, size);
        List<BookDTO> bookDTOList = new ArrayList<>();
        books.forEach(book -> bookDTOList.add(BookDTO.from(book)));
        bookDTOList.sort(Comparator.comparing(BookDTO::getTitle));
        return bookDTOList;
    }

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable long id) {
        Book book = bookService.getBookByID(id);
        return BookDTO.from(book);
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody Book book) {
        Book created = bookService.addBook(book);
        return ResponseEntity.ok(BookDTO.from(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@RequestBody Book book, @PathVariable long id) {
        if (bookService.getBookByID(id) == null) {
            return ResponseEntity.notFound().build();
        }
        book.setId(id);
        Book updated = bookService.updateBook(book);
        return ResponseEntity.ok(BookDTO.from(updated));
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
