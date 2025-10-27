package com.example.expensesplitter.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.expensesplitter.model.Person;
import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {
    Optional<Person> findByEmail(String email);
    boolean existsByEmail(String email);
}
