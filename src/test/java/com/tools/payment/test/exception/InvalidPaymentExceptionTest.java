package com.tools.payment.test.exception;

import com.tools.payment.exception.InvalidPaymentException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvalidPaymentExceptionTest {

    @Test
    public void testInvalidPaymentExceptionMessage() {
        String errorMessage = "Invalid payment method";
        InvalidPaymentException exception = assertThrows(InvalidPaymentException.class, () -> {
            throw new InvalidPaymentException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }
}
