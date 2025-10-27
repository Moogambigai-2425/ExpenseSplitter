package com.example.expensesplitter.service;

import com.example.expensesplitter.model.Expense;
import com.example.expensesplitter.repo.ExpenseRepository;
import com.example.expensesplitter.repo.PersonRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import com.example.expensesplitter.exception.BadRequestException;
import com.example.expensesplitter.exception.ResourceNotFoundException;
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepo;
    private final PersonRepository personRepo;

    public ExpenseService(ExpenseRepository expenseRepo, PersonRepository personRepo) {
        this.expenseRepo = expenseRepo;
        this.personRepo = personRepo;
    }

    public Expense create(Expense expense) {
        // Basic validation: paidBy exists
        if (!personRepo.existsById(expense.getPaidBy())) {
            throw new BadRequestException("paidBy person not found");
        }
        // Validate participants exist
        var missing = expense.getParticipants().stream()
            .map(Expense.ParticipantShare::getPersonId)
            .filter(pid -> !personRepo.existsById(pid)).collect(Collectors.toList());
        if (!missing.isEmpty()) {
            throw new BadRequestException("Participants not found: " + missing);
        }

        // Optional: validate shares add up to amount
        BigDecimal sum = expense.getParticipants().stream()
            .map(Expense.ParticipantShare::getShare)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sum.compareTo(expense.getAmount()) != 0) {
            throw new BadRequestException("Sum of participant shares must equal expense amount");
        }

        return expenseRepo.save(expense);
    }

    public Expense update(String id, Expense expense) {
        expense.setId(id);
        return expenseRepo.save(expense);
    }

    public void delete(String id) { expenseRepo.deleteById(id); }
    public Expense get(String id) { return expenseRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense")); }
    public List<Expense> list() { return expenseRepo.findAll(); }

    // Compute per-person totals across all expenses (net balance: positive means they are owed)
    public Map<String, BigDecimal> computeTotals() {
        List<Expense> all = expenseRepo.findAll();
        Map<String, BigDecimal> balances = new HashMap<>();
        for (Expense e : all) {
            // paidBy increases by amount, participants decrease by their share
            balances.putIfAbsent(e.getPaidBy(), BigDecimal.ZERO);
            balances.put(e.getPaidBy(), balances.get(e.getPaidBy()).add(e.getAmount()));

            for (Expense.ParticipantShare ps : e.getParticipants()) {
                balances.putIfAbsent(ps.getPersonId(), BigDecimal.ZERO);
                balances.put(ps.getPersonId(), balances.get(ps.getPersonId()).subtract(ps.getShare()));
            }
        }
        return balances;
    }
}
