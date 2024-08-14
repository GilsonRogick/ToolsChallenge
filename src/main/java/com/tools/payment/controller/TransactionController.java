package com.tools.payment.controller;

import com.tools.payment.exception.TransactionNotFoundException;
import com.tools.payment.request.TransactionRequest;
import com.tools.payment.response.TransactionResponse;
import com.tools.payment.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/makePayment")
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        try {
            TransactionResponse response = transactionService.createTransaction(transactionRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the exception for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/refundTransaction/{id}")
    public ResponseEntity<TransactionResponse> refundTransaction(@PathVariable("id") Long id) {
        try {
            TransactionResponse response = transactionService.refundTransaction(id);
            return ResponseEntity.ok(response);
        } catch (TransactionNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("selectRefundedTransactionById/{id}")
    public ResponseEntity<TransactionResponse> getRefundedTransactionById(@PathVariable("id") Long id) {
        TransactionResponse response = transactionService.getRefundedTransactionById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/SelectAllRefundedTransaction")
    public ResponseEntity<List<TransactionResponse>> getAllRefundedTransactions() {
        List<TransactionResponse> transactions = transactionService.getAllRefundedTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/selectAllAuthorizedTransactions")
    public ResponseEntity<List<TransactionResponse>> getAllAuthorizedTransactions() {
        List<TransactionResponse> transactions = transactionService.getAllAuthorizedTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/selectAuthorizedTransactionById/{id}")
    public ResponseEntity<TransactionResponse> getAuthorizedTransactionById(@PathVariable("id") Long id) {
        TransactionResponse response = transactionService.getAuthorizedTransactionById(id);
        return ResponseEntity.ok(response);
    }
}
