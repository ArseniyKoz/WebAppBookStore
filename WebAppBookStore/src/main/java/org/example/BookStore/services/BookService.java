package org.example.BookStore.services;

import org.example.BookStore.providers.Book;
import org.example.BookStore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> findAll() {
        return bookRepository.findByActiveTrue();
    }
    @Transactional
    public void save(Book book) {
        book.setActive(true);
        bookRepository.save(book);
    }
    public Optional<Book> findByISBN(String isbn){
        return  bookRepository.findByISBN(isbn);
    }

    public Book findOne(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Optional<Book> findOne(String name, String author) {
        return bookRepository.findByNameAndAuthor(name, author);
    }
    @Transactional
    public void update(int id, Book updateBook) {
        updateBook.setId(id);

        boolean wasActive = bookRepository.findById(id)
                .map(Book::getActive)
                .orElse(true);
        updateBook.setActive(wasActive);
        bookRepository.save(updateBook);
    }

    @Transactional
    public void deactivate(int id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setActive(false);
            bookRepository.save(book);
        });
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    public boolean existsByNameAndAuthor(String name, String author) {
        return bookRepository.findByNameAndAuthor(name, author) != null;
    }

    public boolean existsByIsbn(String isbn) {
        return bookRepository.findByISBN(isbn) != null;
    }
}
