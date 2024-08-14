package com.tools.payment.test.model;

import com.tools.payment.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardNumberTest {

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        transaction = new Transaction();
    }

    @Test
    public void testGetMaskedCardNumber_withValidCardNumber() {
        transaction.setCardNumber("4111111111111111");

        String maskedNumber = transaction.getMaskedCardNumber();

        assertEquals("4111********1111", maskedNumber, "O número do cartão deve estar corretamente mascarado.");
    }

    @Test
    public void testGetMaskedCardNumber_withShortCardNumber() {
        transaction.setCardNumber("1234");

        String maskedNumber = transaction.getMaskedCardNumber();

        assertEquals("1234", maskedNumber, "O número do cartão deve permanecer inalterado se for muito curto.");
    }

    @Test
    public void testGetMaskedCardNumber_withEmptyCardNumber() {
        transaction.setCardNumber("");

        String maskedNumber = transaction.getMaskedCardNumber();

        assertEquals("", maskedNumber, "O número do cartão deve ser uma string vazia se a entrada estiver vazia.");
    }
}