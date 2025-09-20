package br.com.orbitall.channels.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Transactions")
@Data
public class Transaction {

    public enum CardType{
        DEBITO, CREDITO
    }

    @Id
    private UUID id;
    private UUID customerId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private LocalDateTime createdAt;
    private boolean active = true;

    @PrePersist
    protected void onCreate(){
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.active = true ;
    }

}
