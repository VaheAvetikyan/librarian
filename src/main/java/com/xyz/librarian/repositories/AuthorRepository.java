package com.xyz.librarian.repositories;

import com.xyz.librarian.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
