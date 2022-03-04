package com.xyz.librarian.controllers;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.domain.Book;
import com.xyz.librarian.dto.AuthorDTO;
import com.xyz.librarian.dto.BookDTO;
import com.xyz.librarian.services.AuthorService;
import com.xyz.librarian.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class AuthorControllerTest {

    @InjectMocks
    AuthorController authorController;

    @Mock
    AuthorService authorService;
    @Mock
    BookService bookService;

    Author oscarWild;
    Author isaacAsimov;
    AuthorDTO oscarWildDTO;
    AuthorDTO isaacAsimovDTO;

    Book dorianGrey;
    Book foundation;

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

        dorianGrey = new Book("The Picture of Dorian Gray", "1580493939");
        dorianGrey.setId(1L);
        foundation = new Book("Foundation", "9780553293357");
        foundation.setId(2L);
        oscarWild.addToBooks(dorianGrey);
        isaacAsimov.addToBooks(foundation);

        oscarWildDTO = AuthorDTO.from(oscarWild);
        isaacAsimovDTO = AuthorDTO.from(isaacAsimov);
    }

    @Test
    void getAuthors() {
        List<Author> authorList = List.of(oscarWild, isaacAsimov);
        when(authorService.getAuthors()).thenReturn(authorList);

        List<AuthorDTO> authorsRest = authorController.getAuthors();

        assertNotNull(authorsRest);
        AuthorDTO authorDTO1 = authorsRest.get(0);
        AuthorDTO authorDTO2 = authorsRest.get(1);

        assertEquals(isaacAsimov.getFirstName(), authorDTO1.getFirstName());
        assertEquals("Oscar", authorDTO2.getFirstName());
        assertEquals(isaacAsimov.getLastName(), authorDTO1.getLastName());
        assertEquals(oscarWild.getLastName(), authorDTO2.getLastName());
        assertEquals(1, authorDTO1.getBooks().size());
        assertEquals(1, authorDTO2.getBooks().size());

        Iterator<BookDTO> it1 = authorDTO1.getBooks().iterator();
        assertEquals("Foundation", it1.next().getTitle());
        Iterator<BookDTO> it2 = authorDTO2.getBooks().iterator();
        assertEquals("The Picture of Dorian Gray", it2.next().getTitle());
    }

    @Test
    void getAuthor() {
        when(authorService.getAuthorByID(anyLong())).thenReturn(oscarWild);
        ResponseEntity<AuthorDTO> responseEntity = authorController.getAuthor(1);

        assertNotNull(responseEntity);
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).getId());
        assertEquals(oscarWildDTO.getFirstName(), responseEntity.getBody().getFirstName());
        assertEquals(oscarWildDTO.getLastName(), responseEntity.getBody().getLastName());
        assertEquals(oscarWildDTO.getBooks().size(), responseEntity.getBody().getBooks().size());
    }

    @Test
    void addAuthor() {
        when(authorService.addAuthor(any(Author.class))).thenReturn(oscarWild);
        when(bookService.getBooksByID(anySet())).thenReturn(Set.of(dorianGrey));

        ResponseEntity<AuthorDTO> responseEntity = authorController.addAuthor(oscarWild);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(oscarWild.getFirstName(), Objects.requireNonNull(responseEntity.getBody()).getFirstName());
        assertEquals(oscarWild.getBooks().size(), Objects.requireNonNull(responseEntity.getBody()).getBooks().size());
        assertEquals("The Picture of Dorian Gray", Objects.requireNonNull(responseEntity.getBody()).getBooks().iterator().next().getTitle());
    }

    @Test
    void updateAuthor() {
        when(authorService.updateAuthor(any(Author.class))).thenReturn(oscarWild);
        when(bookService.getBooksByID(anySet())).thenReturn(Set.of(dorianGrey, foundation));

        oscarWild.setFirstName("Changed");
        oscarWild.addToBooks(foundation);

        ResponseEntity<AuthorDTO> responseEntity = authorController.updateAuthor(oscarWild, 1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Changed", Objects.requireNonNull(responseEntity.getBody()).getFirstName());

        List<BookDTO> books = new ArrayList<>(Objects.requireNonNull(responseEntity.getBody()).getBooks());
        assertEquals(2, books.size());

        books.sort(Comparator.comparing(BookDTO::getTitle));
        assertEquals("Foundation", books.get(0).getTitle());
        assertEquals("The Picture of Dorian Gray", books.get(1).getTitle());
    }

    @Test
    void deleteAuthor() {
        ResponseEntity<Object> response = authorController.deleteAuthor(101);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
