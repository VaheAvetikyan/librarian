package com.xyz.librarian.controllers;

import com.xyz.librarian.domain.Book;
import com.xyz.librarian.dto.BookDTO;
import com.xyz.librarian.services.BookService;
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
public class BookController {
    private final Logger LOGGER = LogManager.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/books")
    public List<BookDTO> getBooks() {
        Iterable<Book> books = bookService.getBooks();
        List<BookDTO> bookDTOList = new ArrayList<>();
        books.forEach(book -> bookDTOList.add(bookService.convertToDto(book)));
        bookDTOList.sort(Comparator.comparing(BookDTO::getTitle));
        LOGGER.info("Books retrieved [{}]",
                bookDTOList.stream()
                        .map(BookDTO::getTitle)
                        .collect(Collectors.joining(", ")));
        return bookDTOList;
    }

    @RequestMapping("/books/{id}")
    public BookDTO getBook(@PathVariable Long id) {
        Book book = bookService.getBookByID(id);
        return bookService.convertToDto(book);
    }
}
