package com.xyz.librarian.repositories;


import com.xyz.librarian.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
