package com.tools.payment.test.exception;

import com.tools.payment.exception.ErrorResponse;
import com.tools.payment.exception.GlobalExceptionHandler;
import com.tools.payment.exception.InvalidPaymentException;
import com.tools.payment.exception.TransactionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setup() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleInvalidPaymentException() {
        InvalidPaymentException exception = new InvalidPaymentException("Método de pagamento inválido");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidPaymentException(exception);

        assertNotNull(response, "A resposta não pode ser nula");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "O status HTTP deve ser BAD_REQUEST");
        assertEquals("Método de pagamento inválido", response.getBody().getMessage(), "A mensagem de erro deve corresponder");
        assertNotNull(response.getBody().getTimestamp(), "O timestamp não pode ser nulo");
    }

    @Test
    public void testHandleTransactionNotFoundException() {
        TransactionNotFoundException exception = new TransactionNotFoundException("Transação não encontrada");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleTransactionNotFound(exception);

        assertNotNull(response, "A resposta não pode ser nula");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "O status HTTP deve ser NOT_FOUND");
        assertEquals("Transação não encontrada", response.getBody().getMessage(), "A mensagem de erro deve corresponder");
        assertNotNull(response.getBody().getTimestamp(), "O timestamp não pode ser nulo");
    }

    @Test
    public void testHandleGenericException() {
        Exception exception = new Exception("Algum erro genérico");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(exception);

        assertNotNull(response, "A resposta não pode ser nula");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "O status HTTP deve ser INTERNAL_SERVER_ERROR");
        assertEquals("Opa, temos algum problema :/", response.getBody().getMessage(), "A mensagem de erro deve ser padrão para exceções genéricas");
        assertNotNull(response.getBody().getTimestamp(), "O timestamp não pode ser nulo");
    }

    @Test
    public void testHandleExceptionWithNullMessage() {
        // Criar uma exceção genérica com mensagem nula
        Exception exception = new Exception((String) null);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(exception);

        assertNotNull(response, "A resposta não pode ser nula");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "O status HTTP deve ser INTERNAL_SERVER_ERROR");
        assertEquals("Opa, temos algum problema :/", response.getBody().getMessage(), "A mensagem de erro deve ser padrão para exceções genéricas");
        assertNotNull(response.getBody().getTimestamp(), "O timestamp não pode ser nulo");
    }

}
