package br.com.orbitall.channels.canonicals;

import br.com.orbitall.channels.models.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionOutput(
        UUID id,
        UUID customerId,
        BigDecimal amount,
        Transaction.CardType cardType,
        LocalDateTime createdAt,
        boolean active
) {
}

