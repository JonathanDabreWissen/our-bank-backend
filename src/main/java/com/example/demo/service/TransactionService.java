package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.models.Transaction;
import com.example.demo.repositories.TransactionRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    @Transactional
    public String performTransaction(String fromEmail, String toEmail, int amount) {
        if (!userRepo.existsByEmail(fromEmail) || !userRepo.existsByEmail(toEmail)) {
            return "One or both users do not exist!";
        }
        if (fromEmail.equals(toEmail)) {
            return "Cannot transfer to the same account!";
        }
        int updatedRows = userRepo.deductBalance(fromEmail, amount);
        if (updatedRows == 0) {
            return "Insufficient balance!";
        }

        userRepo.addBalance(toEmail, amount);
        Transaction transaction = new Transaction(fromEmail, toEmail, amount);
        transactionRepo.save(transaction);

        return "Transaction successful!";
    }
}
