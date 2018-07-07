package com.challenge.transactionstatistics.controller;

import com.challenge.transactionstatistics.model.Transaction;
import com.challenge.transactionstatistics.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/transactions")
public class TransactionsController {

    private TransactionsService transactionsService;

    @Autowired
    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping
    public ResponseEntity<Void> saveTransaction(@RequestBody Transaction transaction) {
        if (transactionsService.isValidTransaction(transaction)) {
            transactionsService.addTransaction(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
