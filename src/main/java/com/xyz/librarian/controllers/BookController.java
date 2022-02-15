package com.xyz.librarian.controllers;

import com.xyz.librarian.domain.Book;
import com.xyz.librarian.dto.BookDTO;
import com.xyz.librarian.services.BookService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/books")
    public List<BookDTO> getBooks() {
        Iterable<Book> books = bookService.getBooks();
        List<BookDTO> bookDTOList = new ArrayList<>();
        books.forEach(it -> bookDTOList.add(bookService.convertToDto(it)));
        return bookDTOList;
    }

    @RequestMapping("/books/{id}")
    public BookDTO getBook(@PathVariable Long id) {
        Book book = bookService.getBookByID(id);
        return bookService.convertToDto(book);
    }
}
