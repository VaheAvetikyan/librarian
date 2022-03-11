package com.xyz.librarian.services;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.domain.Book;
import com.xyz.librarian.exceptions.AuthorNotFoundException;
import com.xyz.librarian.repositories.AuthorRepository;
import com.xyz.librarian.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    public final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Iterable<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorByID(long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isEmpty()) {
            throw new AuthorNotFoundException("Author with id [" + id + "] does not exist");
        }
        return authorOptional.get();
    }

    public Iterable<Author> getAuthorsByID(Set<Long> ids) {
        return authorRepository.findAllById(ids);
    }

    public Author addAuthor(Author author) {
        if (author.getBooks() != null) {
            assignBooksToAuthor(author);
        }
        return authorRepository.save(author);
    }

    public Author updateAuthor(Author author) {
        if (author.getBooks() != null) {
            assignBooksToAuthor(author);
        }
        return authorRepository.save(author);
    }

    public void removeAuthor(Author author) {
        authorRepository.delete(author);
    }

    private void assignBooksToAuthor(Author author) {
        bookRepository.findAllById(author.getBooks()
                        .stream()
                        .map(Book::getId)
                        .collect(Collectors.toSet()))
                .forEach(book -> book.addToAuthors(author));
    }
}
