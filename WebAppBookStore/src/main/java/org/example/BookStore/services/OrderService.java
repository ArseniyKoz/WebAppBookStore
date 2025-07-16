package org.example.BookStore.services;

import org.example.BookStore.providers.Cart;
import org.example.BookStore.providers.Order;
import org.example.BookStore.providers.OrderItem;
import org.example.BookStore.providers.Person;
import org.example.BookStore.repositories.CartRepository;
import org.example.BookStore.repositories.OrderItemRepository;
import org.example.BookStore.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(CartRepository cartRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public void placeOrder(Person person) {
        List<Cart> cartItems = cartRepository.findByPerson(person);
        Order order = new Order();
        order.setPerson(person);
        order.setOrderDate(LocalDateTime.now());

        double totalOrderPrice = 0.0;
        double totalPrice = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (Cart cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getQuantity()*cartItem.getBook().getPrice());

            totalOrderPrice += orderItem.getTotalPrice();
            orderItems.add(orderItem);
        }

        order.setTotalOrderPrice(totalOrderPrice);
        order.setOrderItems(orderItems);

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        cartRepository.deleteAll(cartItems);
    }

//    public List<Order> showOrders(Person person) {
//        return orderRepository.findByPerson(person);
//    }
    public List<Order> showAllOrders() {
        return orderRepository.findAll();
    }
//
    public List<Order> showOrders(Person person) {
        return orderRepository.findAllByPersonOrderByOrderDateDesc(person);
    }
    public void updateOrderStatus(int orderId, String status) {
        Order order = orderRepository.findById(orderId).get();
        order.setStatus(status);
        orderRepository.save(order);
    }

//    public List<Order> showAllOrders() {
//        return orderRepository.findAllOrdersOrderByOrderDate();
//    }
}

