package com.example.expensesplitter.controller;

import com.example.expensesplitter.model.Expense;
import com.example.expensesplitter.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService srv;
    public ExpenseController(ExpenseService srv) { this.srv = srv; }

    @GetMapping
    public List<Expense> list() { return srv.list(); }

    @PostMapping
    public ResponseEntity<Expense> create(@Valid @RequestBody Expense e) {
        Expense created = srv.create(e);
        return ResponseEntity.created(URI.create("/api/expenses/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public Expense get(@PathVariable String id) { return srv.get(id); }

    @PutMapping("/{id}")
    public Expense update(@PathVariable String id, @Valid @RequestBody Expense e) { return srv.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) { srv.delete(id); return ResponseEntity.noContent().build(); }

    // Custom summary endpoint: per-person totals
    @GetMapping("/totals")
    public Map<String, java.math.BigDecimal> totals() { return srv.computeTotals(); }
}
