package com.xyz.librarian.bootstrap;

import com.xyz.librarian.domain.Address;
import com.xyz.librarian.domain.Author;
import com.xyz.librarian.domain.Book;
import com.xyz.librarian.domain.Publisher;
import com.xyz.librarian.repositories.AddressRepository;
import com.xyz.librarian.repositories.AuthorRepository;
import com.xyz.librarian.repositories.BookRepository;
import com.xyz.librarian.repositories.PublisherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {
    private final Logger LOGGER = LogManager.getLogger(BootstrapData.class);

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AddressRepository addressRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository, AddressRepository addressRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public void run(String... args) {
        LOGGER.info("Bootstrap Started");

        Author ernest = new Author("Ernest", "Hemingway");
        Book fiesta = new Book("The Sun Also Rises", "9780020518709");
        ernest.getBooks().add(fiesta);
        fiesta.addToAuthors(ernest);

        authorRepository.save(ernest);
        bookRepository.save(fiesta);

        Author martin = new Author("Bob", "Martin");
        Book cleanCode = new Book("Clean Code", "9780132350884");
        martin.getBooks().add(cleanCode);
        cleanCode.addToAuthors(martin);

        authorRepository.save(martin);
        bookRepository.save(cleanCode);

        Publisher zangak = new Publisher("Zangak");
        Address address = new Address("7 Abovyan St", "Yerevan", "Yerevan", "0001");
        zangak.setAddress(address);

        addressRepository.save(address);
        publisherRepository.save(zangak);

        fiesta.setPublisher(zangak);
        zangak.getBooks().add(fiesta);

        bookRepository.save(fiesta);
        publisherRepository.save(zangak);

        LOGGER.info("Number of books: {}", bookRepository.count());
        LOGGER.info("Number of Publishers: {}", publisherRepository.count());
        LOGGER.info("Publisher {} - number of books: {}", zangak.getName(), zangak.getBooks().size());
    }
}
