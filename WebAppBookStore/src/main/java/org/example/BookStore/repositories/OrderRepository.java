package org.example.BookStore.repositories;

import org.example.BookStore.providers.Order;
import org.example.BookStore.providers.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByPerson(Person person);
    List<Order> findAllByPersonOrderByOrderDateDesc(Person person);
//
//    @Query("SELECT o FROM Order o ORDER BY o.orderDate DESC")
//    List<Order> findAllOrdersOrderByOrderDate();
}
