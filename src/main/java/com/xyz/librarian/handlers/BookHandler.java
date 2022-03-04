package com.xyz.librarian.handlers;

import com.xyz.librarian.domain.Book;
import com.xyz.librarian.dto.BookDTO;
import com.xyz.librarian.services.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookHandler {
    private final Logger LOGGER = LogManager.getLogger(BookHandler.class);

    private final BookService bookService;

    public BookHandler(BookService bookService) {
        this.bookService = bookService;
    }

    public List<BookDTO> getBooks() {
        Iterable<Book> books = bookService.getBooks();
        List<BookDTO> bookDTOList = new ArrayList<>();
        books.forEach(book -> bookDTOList.add(BookDTO.from(book)));
        LOGGER.debug("Books retrieved [{}]",
                bookDTOList.stream()
                        .map(BookDTO::getTitle)
                        .collect(Collectors.joining(", ")));
        return bookDTOList;
    }

    public List<BookDTO> getBooks(int page, int size) {
        Iterable<Book> books = bookService.getBooks(page, size);
        List<BookDTO> bookDTOList = new ArrayList<>();
        books.forEach(book -> bookDTOList.add(BookDTO.from(book)));
        bookDTOList.sort(Comparator.comparing(BookDTO::getTitle));
        return bookDTOList;
    }

    public BookDTO getBook(long id) {
        Book book = bookService.getBookByID(id);
        return BookDTO.from(book);
    }

    public BookDTO addBook(Book book) {
        Book created = bookService.addBook(book);
        return BookDTO.from(created);
    }

    public BookDTO updateBook(Book book, long id) {
        try {
            bookService.getBookByID(id);
        } catch (RuntimeException e) {
            LOGGER.debug("Book with id {} does not exist", id);
            throw e;
        }
        book.setId(id);
        Book updated = bookService.updateBook(book);
        return BookDTO.from(updated);
    }

    public void deleteBook(long id) {
        Book book = bookService.getBookByID(id);
        if (book == null) {
            return;
        }
        bookService.removeBook(book);
    }
}
