package com.xyz.librarian.repositories;

import com.xyz.librarian.domain.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
