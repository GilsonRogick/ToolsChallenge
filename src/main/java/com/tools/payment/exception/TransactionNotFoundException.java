package com.tools.payment.exception;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(Long id) {
        super("Transação com o id: " + id + " não encontrada!");
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
