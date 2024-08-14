package com.tools.payment.repository;


import com.tools.payment.model.Transaction;
import com.tools.payment.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query(value = "Select nextval('nsu_sequence')", nativeQuery = true)
    Long getNextNsuValue();

    List<Transaction> findByStatus(TransactionStatus status);

    Optional<Transaction> findByTransactionIdAndStatus(String id, TransactionStatus status);
}
