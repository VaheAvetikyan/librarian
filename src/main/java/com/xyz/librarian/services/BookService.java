package com.xyz.librarian.services;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.domain.Book;
import com.xyz.librarian.repositories.AuthorRepository;
import com.xyz.librarian.repositories.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    public final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
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
        if (book.getAuthors() != null) {
            assignAuthorsToBook(book);
        }
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        if (book.getAuthors() != null) {
            assignAuthorsToBook(book);
        }
        return bookRepository.save(book);
    }

    public void removeBook(Book book) {
        bookRepository.delete(book);
    }

    private void assignAuthorsToBook(Book book) {
        authorRepository.findAllById(book.getAuthors()
                        .stream()
                        .map(Author::getId)
                        .collect(Collectors.toSet()))
                .forEach(author -> author.addToBooks(book));
    }
}
