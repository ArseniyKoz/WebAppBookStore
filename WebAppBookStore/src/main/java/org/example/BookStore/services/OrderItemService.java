package org.example.BookStore.services;

import org.example.BookStore.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }


    @Transactional
    public void deleteAllByBookId(int bookId){
        orderItemRepository.deleteAllByBookId(bookId);
    }
}
