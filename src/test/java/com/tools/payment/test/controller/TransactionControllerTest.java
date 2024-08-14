package com.tools.payment.test.controller;

import com.tools.payment.controller.TransactionController;
import com.tools.payment.exception.TransactionNotFoundException;
import com.tools.payment.request.TransactionRequest;
import com.tools.payment.response.TransactionResponse;
import com.tools.payment.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction_Success() {
        TransactionRequest request = new TransactionRequest();
        TransactionResponse response = new TransactionResponse();
        when(transactionService.createTransaction(request)).thenReturn(response);

        ResponseEntity<TransactionResponse> result = transactionController.createTransaction(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void testCreateTransaction_Exception() {
        TransactionRequest request = new TransactionRequest();
        when(transactionService.createTransaction(request)).thenThrow(new RuntimeException());

        ResponseEntity<TransactionResponse> result = transactionController.createTransaction(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testRefundTransaction_Success() {
        Long id = 1L;
        TransactionResponse response = new TransactionResponse();
        when(transactionService.refundTransaction(id)).thenReturn(response);

        ResponseEntity<TransactionResponse> result = transactionController.refundTransaction(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void testRefundTransaction_TransactionNotFound() {
        Long id = 1L;
        when(transactionService.refundTransaction(id)).thenThrow(new TransactionNotFoundException(id));

        ResponseEntity<TransactionResponse> result = transactionController.refundTransaction(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testRefundTransaction_Exception() {
        Long id = 1L;
        when(transactionService.refundTransaction(id)).thenThrow(new RuntimeException());

        ResponseEntity<TransactionResponse> result = transactionController.refundTransaction(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testGetRefundedTransactionById_Success() {
        Long id = 1L;
        TransactionResponse response = new TransactionResponse();
        when(transactionService.getRefundedTransactionById(id)).thenReturn(response);

        ResponseEntity<TransactionResponse> result = transactionController.getRefundedTransactionById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void testGetAllRefundedTransactions() {
        List<TransactionResponse> responses = Collections.singletonList(new TransactionResponse());
        when(transactionService.getAllRefundedTransactions()).thenReturn(responses);

        ResponseEntity<List<TransactionResponse>> result = transactionController.getAllRefundedTransactions();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responses, result.getBody());
    }

    @Test
    public void testGetAllAuthorizedTransactions() {
        List<TransactionResponse> responses = Collections.singletonList(new TransactionResponse());
        when(transactionService.getAllAuthorizedTransactions()).thenReturn(responses);

        ResponseEntity<List<TransactionResponse>> result = transactionController.getAllAuthorizedTransactions();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responses, result.getBody());
    }

    @Test
    public void testGetAuthorizedTransactionById_Success() {
        Long id = 1L;
        TransactionResponse response = new TransactionResponse();
        when(transactionService.getAuthorizedTransactionById(id)).thenReturn(response);

        ResponseEntity<TransactionResponse> result = transactionController.getAuthorizedTransactionById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
}
