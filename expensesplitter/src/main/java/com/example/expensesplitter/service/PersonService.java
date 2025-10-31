package com.example.expensesplitter.service;

import com.example.expensesplitter.model.Person;
import com.example.expensesplitter.repo.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import java.util.List;
import com.example.expensesplitter.exception.ResourceNotFoundException;
@Service
public class PersonService {
    private final PersonRepository repo;
    public PersonService(PersonRepository repo) { this.repo = repo; }

    public Person create(Person p) {
        if (p.getEmail() != null && repo.existsByEmail(p.getEmail())) {
            throw new DuplicateKeyException("Email already in use");
        }
        return repo.save(p);
    }
    public Person update(String id, Person p) {
        p.setId(id);
        return repo.save(p);
    }
    public Person get(String id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person")); }
    public void delete(String id) { repo.deleteById(id); }
    public List<Person> list() { return repo.findAll(); }
}