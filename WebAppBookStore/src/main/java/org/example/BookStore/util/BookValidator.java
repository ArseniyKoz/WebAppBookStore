package org.example.BookStore.util;

import org.example.BookStore.providers.Book;
import org.example.BookStore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

import static org.example.BookStore.services.URLService.checkURL;

@Component
public class BookValidator implements Validator {

    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (errors.hasFieldErrors("year") && Objects.equals((errors.getFieldError("year")).getCode(), "typeMismatch"))
            errors.rejectValue("year", "", "Неверный формат");
        if (errors.hasFieldErrors("price") && Objects.equals((errors.getFieldError("price")).getCode(), "typeMismatch"))
            errors.rejectValue("price", "", "Неверный формат");
        if (errors.hasFieldErrors("pageCount") && Objects.equals((errors.getFieldError("pageCount")).getCode(), "typeMismatch"))
            errors.rejectValue("pageCount", "", "Неверный формат");
        if (errors.hasFieldErrors("rating") && Objects.equals((errors.getFieldError("rating")).getCode(), "typeMismatch"))
            errors.rejectValue("rating", "", "Неверный формат");
        if (errors.hasFieldErrors("quantity") && Objects.equals((errors.getFieldError("quantity")).getCode(), "typeMismatch"))
            errors.rejectValue("quantity", "", "Неверный формат");
        if (bookService.findOne(book.getName(), book.getAuthor()).isPresent()) {
            errors.rejectValue("name", "", "Книга с таким автором и названием уже" +
                    " существует");
            errors.rejectValue("author", "", "Книга с таким автором и названием уже" +
                    " существует");
        }
        if (bookService.findByISBN(book.getISBN()).isPresent()) {
            errors.rejectValue("ISBN","", "Книга с таким ISBN уже существует");
        }
    }
}
