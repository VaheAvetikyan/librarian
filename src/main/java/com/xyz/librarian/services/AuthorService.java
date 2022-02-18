package com.xyz.librarian.services;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.exceptions.AuthorNotFoundException;
import com.xyz.librarian.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
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
        return authorRepository.save(author);
    }

    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void removeAuthor(Author author) {
        authorRepository.delete(author);
    }
}
