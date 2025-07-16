package org.example.BookStore.repositories;

import org.example.BookStore.providers.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByNameAndAuthor(String name, String author);
    Optional<Book> findByISBN(String isbn);
    List<Book> findByActiveTrue();
}
