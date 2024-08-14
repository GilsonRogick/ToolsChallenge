package com.tools.payment.test.mapper;

import com.tools.payment.mapper.TransactionMapper;
import com.tools.payment.model.PaymentMethodType;
import com.tools.payment.model.Transaction;
import com.tools.payment.model.TransactionStatus;
import com.tools.payment.request.DescriptionRequest;
import com.tools.payment.request.PaymentMethodRequest;
import com.tools.payment.request.TransactionRequest;
import com.tools.payment.response.TransactionResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionMapperTest {

    private final TransactionMapper transactionMapper = new TransactionMapper();

    @Test
    public void testConvertPaymentMethodType_ValidType() {
        PaymentMethodType type = transactionMapper.convertPaymentMethodType("AVISTA");

        assertEquals(PaymentMethodType.AVISTA, type);
    }

    @Test
    public void testConvertPaymentMethodType_InvalidType() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                transactionMapper.convertPaymentMethodType("INVALID_TYPE")
        );
        assertEquals("Tipo de pagamento inválido: INVALID_TYPE", thrown.getMessage());
    }

    @Test
    public void testConvertTransactionStatus_ValidStatus() {
        TransactionStatus status = transactionMapper.convertTransactionStatus("AUTHORIZED");

        assertEquals(TransactionStatus.AUTHORIZED, status);
    }

    @Test
    public void testConvertTransactionStatus_InvalidStatus() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                transactionMapper.convertTransactionStatus("INVALID_STATUS")
        );
        assertEquals("Status de transação inválido: INVALID_STATUS", thrown.getMessage());
    }

    @Test
    public void testConvertToModel() {
        TransactionRequest request = new TransactionRequest();
        request.setId("12345");
        request.setCardNumber("4111111111111111");

        DescriptionRequest descriptionRequest = new DescriptionRequest();
        descriptionRequest.setAmount(new BigDecimal("150.00"));
        descriptionRequest.setDateTime(LocalDateTime.now());
        descriptionRequest.setEstablishment("Store");

        PaymentMethodRequest paymentMethodRequest = new PaymentMethodRequest();
        paymentMethodRequest.setType("AVISTA");
        paymentMethodRequest.setInstallments(1);

        request.setDescriptionRequest(descriptionRequest);
        request.setPaymentMethodRequest(paymentMethodRequest);

        Transaction transaction = transactionMapper.convertToModel(request);

        assertNotNull(transaction);
        assertEquals(request.getId(), transaction.getTransactionId());
        assertEquals(request.getCardNumber(), transaction.getCardNumber());
        assertEquals(request.getDescriptionRequest().getAmount(), transaction.getAmount());
        assertEquals(request.getDescriptionRequest().getDateTime(), transaction.getDateTime());
        assertEquals(request.getDescriptionRequest().getEstablishment(), transaction.getEstablishment());
        assertEquals(PaymentMethodType.AVISTA, transaction.getType());
        assertEquals(request.getPaymentMethodRequest().getInstallments(), transaction.getInstallments());
        assertEquals(TransactionStatus.AUTHORIZED, transaction.getStatus()); // Default value
    }
    @Test
    public void testConverteToDto() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("12345");
        transaction.setCardNumber("4111111111111111"); // Número completo do cartão
        transaction.setAmount(new BigDecimal("150.00"));
        transaction.setDateTime(LocalDateTime.now());
        transaction.setEstablishment("Store");
        transaction.setType(PaymentMethodType.AVISTA);
        transaction.setInstallments(1);
        transaction.setStatus(TransactionStatus.AUTHORIZED);

        TransactionResponse response = transactionMapper.converteToDto(transaction);

        assertNotNull(response);
        assertEquals(transaction.getTransactionId(), response.getId());

        assertEquals(transaction.getMaskedCardNumber(), response.getCardNumber());
        assertEquals(transaction.getAmount(), response.getDescriptionRequest().getAmount());
        assertEquals(transaction.getDateTime(), response.getDescriptionRequest().getDateTime());
        assertEquals(transaction.getEstablishment(), response.getDescriptionRequest().getEstablishment());
        assertEquals(transaction.getType().name(), response.getPaymentMethodResponse().getType());
        assertEquals(transaction.getInstallments(), response.getPaymentMethodResponse().getInstallments());
        assertEquals(transaction.getStatus().name(), response.getDescriptionRequest().getStatus());
    }

    @Test
    public void testToDtoList() {
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId("12345");
        transaction1.setCardNumber("4111111111111111"); // Número completo do cartão
        transaction1.setAmount(new BigDecimal("150.00"));
        transaction1.setDateTime(LocalDateTime.now());
        transaction1.setEstablishment("Store");
        transaction1.setType(PaymentMethodType.AVISTA);
        transaction1.setInstallments(1);
        transaction1.setStatus(TransactionStatus.AUTHORIZED);

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId("67890");
        transaction2.setCardNumber("5111111111111111"); // Número completo do cartão
        transaction2.setAmount(new BigDecimal("250.00"));
        transaction2.setDateTime(LocalDateTime.now());
        transaction2.setEstablishment("Store 2");
        transaction2.setType(PaymentMethodType.PARCELADO_EMISSOR);
        transaction2.setInstallments(2);
        transaction2.setStatus(TransactionStatus.CANCELLED);

        List<Transaction> transactions = List.of(transaction1, transaction2);

        List<TransactionResponse> responses = transactionMapper.toDtoList(transactions);

        assertNotNull(responses);
        assertEquals(transactions.size(), responses.size());

        TransactionResponse response1 = responses.get(0);
        assertEquals(transaction1.getTransactionId(), response1.getId());

        assertEquals(transaction1.getMaskedCardNumber(), response1.getCardNumber());

        TransactionResponse response2 = responses.get(1);
        assertEquals(transaction2.getTransactionId(), response2.getId());

        assertEquals(transaction2.getMaskedCardNumber(), response2.getCardNumber());
    }
}
