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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAuthors() {
        Author authorEntity1 = new Author();
        authorEntity1.setId(1L);
        authorEntity1.setFirstName("Oscar");
        authorEntity1.setLastName("Wild");
        Author authorEntity2 = new Author();
        authorEntity2.setId(2L);
        authorEntity2.setFirstName("Isaac");
        authorEntity2.setLastName("Asimov");
        Iterable<Author> authorEntities = List.of(authorEntity1, authorEntity2);
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
        Author authorEntity = new Author();
        authorEntity.setId(1L);
        authorEntity.setFirstName("Oscar");
        authorEntity.setLastName("Wild");
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(authorEntity));

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
        Author authorEntity1 = new Author();
        authorEntity1.setId(1L);
        authorEntity1.setFirstName("Oscar");
        authorEntity1.setLastName("Wild");
        Author authorEntity2 = new Author();
        authorEntity2.setId(2L);
        authorEntity2.setFirstName("Isaac");
        authorEntity2.setLastName("Asimov");
        Iterable<Author> authorEntities = List.of(authorEntity1, authorEntity2);
        when(authorRepository.findAllById(anySet())).thenReturn(authorEntities);

        Iterable<Author> authors = authorService.getAuthorsByID(Set.of(1L, 2L));

        Iterator<Author> it = authors.iterator();
        Author oscarWild = it.next();
        assertNotNull(oscarWild);
        assertEquals("Oscar", oscarWild.getFirstName());
        assertEquals("Wild", oscarWild.getLastName());
        Author isaacAsimov = it.next();
        assertNotNull(isaacAsimov);
        assertEquals("Isaac", isaacAsimov.getFirstName());
        assertEquals("Isaac", isaacAsimov.getFirstName());
        assertFalse(it.hasNext());
    }

    @Test
    void addAuthor() {
        Author authorEntity = new Author();
        authorEntity.setId(1L);
        authorEntity.setFirstName("Oscar");
        authorEntity.setLastName("Wild");

        when(authorRepository.save(any())).thenReturn(authorEntity);

        Author author = authorService.addAuthor(authorEntity);
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