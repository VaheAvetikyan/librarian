package com.xyz.librarian.services;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.domain.Book;
import com.xyz.librarian.dto.AuthorDTO;
import com.xyz.librarian.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

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
            throw new RuntimeException("Author with id [" + id + "] does not exist");
        }
        return authorOptional.get();
    }

    public AuthorDTO convertToDto(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());
        authorDTO.setBooks(author.getBooks().stream().mapToLong(Book::getId).boxed().collect(Collectors.toSet()));
        return authorDTO;
    }
}
