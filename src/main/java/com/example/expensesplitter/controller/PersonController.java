package com.example.expensesplitter.controller;

import com.example.expensesplitter.model.Person;
import com.example.expensesplitter.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PersonController {
    private final PersonService srv;
    public PersonController(PersonService srv) { this.srv = srv; }

    @GetMapping
    public List<Person> list() { return srv.list(); }

    @PostMapping
    public ResponseEntity<Person> create(@Validated @RequestBody Person p) {
        Person created = srv.create(p);
        return ResponseEntity.created(URI.create("/api/people/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public Person get(@PathVariable String id) { return srv.get(id); }

    @PutMapping("/{id}")
    public Person update(@PathVariable String id, @Validated @RequestBody Person p) { return srv.update(id, p); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) { srv.delete(id); return ResponseEntity.noContent().build(); }
}
