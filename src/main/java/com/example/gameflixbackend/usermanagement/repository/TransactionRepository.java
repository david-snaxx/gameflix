package com.example.gameflixbackend.usermanagement.repository;

import com.example.gameflixbackend.usermanagement.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    /**
     * Finds all transactions for a user, sorted by newest first.
     */
    List<Transaction> findByUserIdOrderByDateDesc(Long userId);
}