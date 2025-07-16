package org.example.BookStore.repositories;

import org.example.BookStore.providers.Cart;
import org.example.BookStore.providers.Person;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    public Cart findByPersonIdAndBookId(Integer person_id, Integer book_id);
    public List<Cart> findAllByPersonRole(String personRole);
    public Integer countByPersonId(Integer person_id);
    public List<Cart> findByPerson(Person person);
    @Transactional
    void deleteAllByBookId(int id);

}
