package br.com.orbitall.channels.controllers;


import br.com.orbitall.channels.canonicals.TransactionInput;
import br.com.orbitall.channels.canonicals.TransactionOutput;
import br.com.orbitall.channels.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionOutput> create(@Valid @RequestBody TransactionInput input) {
        TransactionOutput transaction = transactionService.create(input);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransactionOutput>> findByCustomerId(@RequestParam UUID customerId) {
        List<TransactionOutput> transactions = transactionService.findByCustomerId(customerId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
