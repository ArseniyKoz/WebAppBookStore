package org.example.BookStore.controller;


import org.example.BookStore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    private final CartService cartService;

    @Autowired
    public UserRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add-order/{person_id}/{book_id}")
    public String addOrder(Model model, @PathVariable int person_id, @PathVariable int book_id){
        cartService.saveCart(person_id, book_id);
        return "Item added to cart";
    }
}
