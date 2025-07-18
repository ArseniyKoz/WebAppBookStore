package org.example.BookStore.controller;

import org.example.BookStore.providers.Book;
import org.example.BookStore.providers.Order;
import org.example.BookStore.providers.Person;
import org.example.BookStore.services.*;
import org.example.BookStore.util.BookValidator;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class AdminController {
    private final BookValidator bookValidator;
    private final BookService bookService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final PersonService personService;
    private final CartService cartService;

    public AdminController(BookValidator bookValidator, BookService bookService,
                           OrderService orderService, OrderItemService orderItemService,
                           PersonService personService, CartService cartService) {
        this.bookValidator = bookValidator;
        this.bookService = bookService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.personService = personService;
        this.cartService = cartService;
    }

    @GetMapping("/add-book")
    public String addBook(@ModelAttribute("book") Book book){
        return "addBook";
    }

    @PostMapping("/add-book")
    public String createBook(Model model,
                             @Valid @ModelAttribute("book") Book book,
                             BindingResult bindingResult) {

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors: " + bindingResult.getAllErrors());
            return "addBook";
        }

        try {
            bookService.save(book);
            model.addAttribute("postCorrect", true);
            // Сбрасываем форму
            model.addAttribute("book", new Book());
        } catch (Exception e) {
            System.out.println("Error saving book: " + e.getMessage());
            model.addAttribute("saveError", "Ошибка при сохранении книги");
        }

        return "addBook";
    }

    @Transactional
    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        cartService.deleteAll(id);
        orderItemService.deleteAllByBookId(id);
        bookService.delete(id);
        return "redirect:/bookDetails";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String updateBook(@PathVariable("id") int id,
                             @Valid @ModelAttribute("book") Book book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        bookService.update(id, book);
        return "redirect:/bookDetails";
    }

    @GetMapping("/bookDetails")
    public String bookDetails(Model model) {
        model.addAttribute("bookList", bookService.findAll());
        return "bookDetails";
    }

    @GetMapping("/users")
    public String getAllOrders(Authentication authentication, Model model) {
        List<Order> orders = orderService.showAllOrders();
        Map<Person, List<Order>> personOrdersMap = orders.stream()
                .collect(Collectors.groupingBy(Order::getPerson));
        model.addAttribute("personOrdersMap", personOrdersMap);
        return "users";
    }

    @PostMapping("/updateOrderStatus")
    public String updateOrderStatus(@RequestParam("orderId") int orderId,
                                    @RequestParam("status") String status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/users";
    }

    @PostMapping("/{id}/deactivate")
    public String deactivateBook(@PathVariable("id") int id) {
        bookService.deactivate(id);
        return "redirect:/bookDetails";
    }

    @GetMapping("/listOfUsers")
    public String getAllPersons(Authentication authentication, Model model) {
        List<Person> persons = personService.showAllPersons();
        Map<Person, List<Order>> personMap = persons.stream()
                .collect(Collectors.toMap(
                        person -> person,
                        person -> Collections.emptyList()
                ));

        model.addAttribute("personMap", personMap);
        return "listOfUsers";
    }

    @PostMapping("/updatePersonRole")
    public String updatePersonRole(@RequestParam("personId") int personId,
                                   @RequestParam("role") String role) {
        personService.updatePersonRole(personId, role);
        return "redirect:/listOfUsers";
    }


}