package com.tools.payment.service;

import com.tools.payment.exception.TransactionNotFoundException;
import com.tools.payment.mapper.TransactionMapper;
import com.tools.payment.model.Transaction;
import com.tools.payment.model.TransactionStatus;
import com.tools.payment.repository.TransactionRepository;
import com.tools.payment.request.TransactionRequest;
import com.tools.payment.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = transactionMapper.convertToModel(transactionRequest);
        transaction.setNsu(transactionRepository.getNextNsuValue());
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.converteToDto(savedTransaction);
    }

    @Transactional
    public TransactionResponse refundTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new TransactionNotFoundException(id));
        transaction.setStatus(TransactionStatus.CANCELLED);
        Transaction refundedTransaction = transactionRepository.save(transaction);
        return transactionMapper.converteToDto(refundedTransaction);
    }

    @Transactional(readOnly = true)
    public TransactionResponse getRefundedTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new TransactionNotFoundException(id));
        return transactionMapper.converteToDto(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getAllRefundedTransactions() {
        List<Transaction> transactions = transactionRepository.findByStatus(TransactionStatus.CANCELLED);
        return transactionMapper.toDtoList(transactions);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getAllAuthorizedTransactions() {
        List<Transaction> transactions = transactionRepository.findByStatus(TransactionStatus.AUTHORIZED);
        return transactionMapper.toDtoList(transactions);
    }

    @Transactional(readOnly = true)
    public TransactionResponse getAuthorizedTransactionById(Long id) {
        Transaction transaction = transactionRepository.findByTransactionIdAndStatus(String.valueOf(id), TransactionStatus.AUTHORIZED)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        return transactionMapper.converteToDto(transaction);
    }
}
