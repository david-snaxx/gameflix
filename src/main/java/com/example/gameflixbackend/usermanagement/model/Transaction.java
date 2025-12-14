package com.example.gameflixbackend.usermanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user_transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "card_name", nullable = false)
    private String nameOnCard;

    @Column(name = "card_last_digits", nullable = false)
    private String lastDigits;

    @Column(name = "card_expiration", nullable = false)
    private String expiration;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "amount", nullable = false)
    private Double amount;

    public Transaction() {}

    public Transaction(Long userId, String nameOnCard, String lastDigits, String expiration, Double amount) {
        this.userId = userId;
        this.nameOnCard = nameOnCard;
        this.lastDigits = lastDigits;
        this.expiration = expiration;
        this.date = LocalDateTime.now();
        this.amount = amount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getNameOnCard() { return nameOnCard; }
    public void setNameOnCard(String nameOnCard) { this.nameOnCard = nameOnCard; }

    public String getLastDigits() { return lastDigits; }
    public void setLastDigits(String lastDigits) { this.lastDigits = lastDigits; }

    public String getExpiration() { return expiration; }
    public void setExpiration(String expiration) { this.expiration = expiration; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transaction that = (Transaction) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}