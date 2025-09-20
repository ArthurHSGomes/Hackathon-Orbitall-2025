package br.com.orbitall.channels.canonicals;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionInput(

        @NotNull(message = "Client ID cannot be null or empty")
        UUID customerId,

        @NotNull(message = "The transaction amout cannot be null")
        @DecimalMin(value = "0.01", message = "Transaction amount must be greater than zero")
        BigDecimal amount,

        @NotBlank(message = "Card type cannot be empty")
        @Pattern(regexp = "^(DEBITO|CREDITO)$", message = "Card type must be DEBIT or CREDIT")
        String cardtype
) {
}
