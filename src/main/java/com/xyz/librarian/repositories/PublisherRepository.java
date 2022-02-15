package com.xyz.librarian.repositories;

import com.xyz.librarian.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
