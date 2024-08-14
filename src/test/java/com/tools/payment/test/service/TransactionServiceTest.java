package com.tools.payment.test.service;

import com.tools.payment.exception.TransactionNotFoundException;
import com.tools.payment.mapper.TransactionMapper;
import com.tools.payment.model.Transaction;
import com.tools.payment.model.TransactionStatus;
import com.tools.payment.repository.TransactionRepository;
import com.tools.payment.request.TransactionRequest;
import com.tools.payment.response.TransactionResponse;
import com.tools.payment.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransaction_ShouldReturnTransactionResponse() {
        TransactionRequest request = new TransactionRequest();
        Transaction transaction = new Transaction();
        TransactionResponse response = new TransactionResponse();

        when(transactionMapper.convertToModel(request)).thenReturn(transaction);
        when(transactionRepository.getNextNsuValue()).thenReturn(Long.valueOf("12345"));
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.converteToDto(transaction)).thenReturn(response);

        TransactionResponse result = transactionService.createTransaction(request);

        assertThat(result).isEqualTo(response);
        verify(transactionRepository).save(transaction);
    }

    @Test
    void refundTransaction_ShouldThrowTransactionNotFoundException() {
        Long id = 1L;

        when(transactionRepository.findById(String.valueOf(id))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.refundTransaction(id))
                .isInstanceOf(TransactionNotFoundException.class)
                .hasMessage("Transação com o id: 1 não encontrada!");
    }

    @Test
    void getRefundedTransactionById_ShouldReturnTransactionResponse() {
        Long id = 1L;
        Transaction transaction = new Transaction();
        TransactionResponse response = new TransactionResponse();

        when(transactionRepository.findById(String.valueOf(id))).thenReturn(Optional.of(transaction));
        when(transactionMapper.converteToDto(transaction)).thenReturn(response);

        TransactionResponse result = transactionService.getRefundedTransactionById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void getRefundedTransactionById_ShouldThrowTransactionNotFoundException() {
        Long id = 1L;

        when(transactionRepository.findById(String.valueOf(id))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.getRefundedTransactionById(id))
                .isInstanceOf(TransactionNotFoundException.class)
                .hasMessage("Transação com o id: 1 não encontrada!");
    }

    @Test
    void getAllRefundedTransactions_ShouldReturnTransactionResponseList() {
        Transaction transaction = new Transaction();
        List<Transaction> transactions = List.of(transaction);
        List<TransactionResponse> responses = List.of(new TransactionResponse());

        when(transactionRepository.findByStatus(TransactionStatus.CANCELLED)).thenReturn(transactions);
        when(transactionMapper.toDtoList(transactions)).thenReturn(responses);

        List<TransactionResponse> result = transactionService.getAllRefundedTransactions();

        assertThat(result).isEqualTo(responses);
    }

    @Test
    void getAllAuthorizedTransactions_ShouldReturnTransactionResponseList() {
        Transaction transaction = new Transaction();
        List<Transaction> transactions = List.of(transaction);
        List<TransactionResponse> responses = List.of(new TransactionResponse());

        when(transactionRepository.findByStatus(TransactionStatus.AUTHORIZED)).thenReturn(transactions);
        when(transactionMapper.toDtoList(transactions)).thenReturn(responses);

        List<TransactionResponse> result = transactionService.getAllAuthorizedTransactions();

        assertThat(result).isEqualTo(responses);
    }

    @Test
    void getAuthorizedTransactionById_ShouldReturnTransactionResponse() {
        Long id = 1L;
        Transaction transaction = new Transaction();
        TransactionResponse response = new TransactionResponse();

        when(transactionRepository.findByTransactionIdAndStatus(String.valueOf(id), TransactionStatus.AUTHORIZED))
                .thenReturn(Optional.of(transaction));
        when(transactionMapper.converteToDto(transaction)).thenReturn(response);

        TransactionResponse result = transactionService.getAuthorizedTransactionById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void getAuthorizedTransactionById_ShouldThrowTransactionNotFoundException() {
        Long id = 1L;

        when(transactionRepository.findByTransactionIdAndStatus(String.valueOf(id), TransactionStatus.AUTHORIZED))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.getAuthorizedTransactionById(id))
                .isInstanceOf(TransactionNotFoundException.class)
                .hasMessage("Transação com o id: 1 não encontrada!");
    }
}
