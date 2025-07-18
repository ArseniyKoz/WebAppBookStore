package org.example.BookStore.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.BookStore.providers.Order;
import org.example.BookStore.providers.Person;
import org.example.BookStore.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Person> getPerson(String username) {
        return personRepository.findByUsername(username);
    }
    @Transactional
    public void save(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }

    public List<Person> showAllPersons() {
        return personRepository.findAll();
    }

    @Transactional
    public void updatePersonRole(int userId, String newRole) {
        Person person = personRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Запрещаем изменение роли админа
        if ("ROLE_ADMIN".equals(person.getRole())) {
            throw new SecurityException("Cannot change admin role");
        }

        person.setRole(newRole);
        personRepository.save(person);
    }

}
