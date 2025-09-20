package br.com.orbitall.channels.services;
import br.com.orbitall.channels.canonicals.TransactionInput;
import br.com.orbitall.channels.exceptions.ResourceNotFoundException;
import br.com.orbitall.channels.models.Customer;
import br.com.orbitall.channels.models.Transaction;
import br.com.orbitall.channels.repositories.TransactionRepository;
import br.com.orbitall.channels.canonicals.TransactionOutput;
import br.com.orbitall.channels.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;


    private TransactionOutput mapToTransactionOutput(Transaction transaction) {
        return new TransactionOutput(
                transaction.getId(),
                transaction.getCustomerId(),
                transaction.getAmount(),
                transaction.getCardType(),
                transaction.getCreatedAt(),
                transaction.isActive()
        );
    }
    public TransactionOutput create(TransactionInput input) {
        customerRepository.findById(input.customerId())
                .filter(Customer::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found or inactive with ID: " + input.customerId()));

        Transaction transaction = new Transaction();
        transaction.setCustomerId(input.customerId());
        transaction.setAmount(input.amount());
        transaction.setCardType(Transaction.CardType.valueOf(input.cardtype().toUpperCase()));

        Transaction savedTransaction = transactionRepository.save(transaction);
        return mapToTransactionOutput(savedTransaction);
    }
    public List<TransactionOutput> findByCustomerId(UUID customerId) {
        customerRepository.findById(customerId)
                .filter(Customer::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found or inactive with ID: " + customerId));
        return transactionRepository.findByCustomerId(customerId).stream()
                .filter(Transaction::isActive)
                .map(this::mapToTransactionOutput)
                .collect(Collectors.toList());
    }
}

