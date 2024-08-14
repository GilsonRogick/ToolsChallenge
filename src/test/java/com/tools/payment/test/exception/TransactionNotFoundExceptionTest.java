package com.tools.payment.test.exception;

import com.tools.payment.exception.TransactionNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionNotFoundExceptionTest {

    @Test
    public void testTransactionNotFoundExceptionMessage() {
        String errorMessage = "Transaction not found";
        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class, () -> {
            throw new TransactionNotFoundException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }
}
