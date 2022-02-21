package com.xyz.librarian.dto;

import com.xyz.librarian.domain.Author;
import com.xyz.librarian.domain.Book;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDTOTest {

    @Test
    void from() {
        Author oscarWild = new Author();
        oscarWild.setId(1L);
        oscarWild.setFirstName("Oscar");
        oscarWild.setLastName("Wild");
        Book dorianGrey = new Book("The Picture of Dorian Gray", "1580493939");
        dorianGrey.setId(1L);
        oscarWild.addToBooks(dorianGrey);

        AuthorDTO authorDTO = AuthorDTO.from(oscarWild);

        assertEquals(oscarWild.getId(), authorDTO.getId());
        assertEquals(oscarWild.getFirstName(), authorDTO.getFirstName());
        assertEquals(oscarWild.getLastName(), authorDTO.getLastName());
        assertEquals(oscarWild.getBooks().size(), authorDTO.getBooks().size());

        Optional<BookDTO> bookOptional = authorDTO.getBooks().stream().findFirst();
        assertTrue(bookOptional.isPresent());
        assertEquals("The Picture of Dorian Gray", bookOptional.get().getTitle());
    }
}