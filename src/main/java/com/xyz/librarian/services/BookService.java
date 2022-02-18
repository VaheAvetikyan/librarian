package com.xyz.librarian.services;

import com.xyz.librarian.domain.Book;
import com.xyz.librarian.repositories.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    public final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> getBooks() {
        return bookRepository.findAll(PageRequest.ofSize(10).withSort(Sort.by("title").ascending()));
    }

    public Iterable<Book> getBooks(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size, Sort.by("title").ascending()));
    }

    public Book getBookByID(long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new RuntimeException("Book with id [" + id + "] does not exist");
        }
        return bookOptional.get();
    }

    public Iterable<Book> getBooksByID(Set<Long> ids) {
        return bookRepository.findAllById(ids);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public void removeBook(Book book) {
        bookRepository.delete(book);
    }
}
