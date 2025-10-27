package com.example.expensesplitter.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.expensesplitter.model.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, String> { }
