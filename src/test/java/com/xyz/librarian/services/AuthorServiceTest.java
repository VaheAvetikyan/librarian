package com.xyz.librarian.services;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.exceptions.AuthorNotFoundException;
import com.xyz.librarian.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class AuthorServiceTest {

    @InjectMocks
    AuthorService authorService;

    @Mock
    AuthorRepository authorRepository;

    Author oscarWild;
    Author isaacAsimov;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        oscarWild = new Author();
        oscarWild.setId(1L);
        oscarWild.setFirstName("Oscar");
        oscarWild.setLastName("Wild");
        isaacAsimov = new Author();
        isaacAsimov.setId(2L);
        isaacAsimov.setFirstName("Isaac");
        isaacAsimov.setLastName("Asimov");
    }

    @Test
    void getAuthors() {

        Iterable<Author> authorEntities = List.of(oscarWild, isaacAsimov);
        when(authorRepository.findAllById(anySet())).thenReturn(authorEntities);

        Iterable<Author> authors = authorService.getAuthorsByID(Set.of(1L));
        Iterator<Author> it = authors.iterator();
        Author oscarWild = it.next();
        assertEquals("Oscar", oscarWild.getFirstName());
        assertEquals("Wild", oscarWild.getLastName());
        Author isaacAsimov = it.next();
        assertEquals("Isaac", isaacAsimov.getFirstName());
        assertEquals("Isaac", isaacAsimov.getFirstName());
        assertFalse(it.hasNext());
    }

    @Test
    void getAuthorByID() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(oscarWild));

        Author author = authorService.getAuthorByID(1L);

        assertNotNull(author);
        assertEquals("Oscar", author.getFirstName());
        assertEquals("Wild", author.getLastName());
    }

    @Test
    void testAuthorByIDNotFound() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorByID(1L));
        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorByID(2L));
    }

    @Test
    void getAuthorsByID() {
        Iterable<Author> authorEntities = List.of(oscarWild, isaacAsimov);
        when(authorRepository.findAllById(anySet())).thenReturn(authorEntities);

        Iterable<Author> authors = authorService.getAuthorsByID(Set.of(1L, 2L));

        Iterator<Author> it = authors.iterator();
        Author author1 = it.next();
        assertNotNull(author1);
        assertEquals("Oscar", author1.getFirstName());
        assertEquals("Wild", author1.getLastName());
        Author author2 = it.next();
        assertNotNull(author2);
        assertEquals("Isaac", author2.getFirstName());
        assertEquals("Isaac", author2.getFirstName());
        assertFalse(it.hasNext());
    }

    @Test
    void addAuthor() {
        when(authorRepository.save(any())).thenReturn(oscarWild);

        Author author = authorService.addAuthor(oscarWild);
        assertNotNull(author);
        assertEquals(1L, author.getId());
        assertEquals("Oscar", author.getFirstName());
        assertEquals("Wild", author.getLastName());
    }

    @Test
    void updateAuthor() {
    }

    @Test
    void removeAuthor() {
    }
}