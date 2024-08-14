package com.tools.payment.mapper;

import com.tools.payment.model.PaymentMethodType;
import com.tools.payment.model.Transaction;
import com.tools.payment.model.TransactionStatus;
import com.tools.payment.request.TransactionRequest;
import com.tools.payment.response.DescriptionResponse;
import com.tools.payment.response.PaymentMethodResponse;
import com.tools.payment.response.TransactionResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public Transaction convertToModel(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(request.getId());
        transaction.setCardNumber(request.getCardNumber());
        transaction.setAmount(request.getDescriptionRequest().getAmount());
        transaction.setDateTime(request.getDescriptionRequest().getDateTime());
        transaction.setEstablishment(request.getDescriptionRequest().getEstablishment());

        // Convertendo e definindo o tipo de pagamento
        transaction.setType(convertPaymentMethodType(request.getPaymentMethodRequest().getType()));
        transaction.setInstallments(request.getPaymentMethodRequest().getInstallments());

        // Definindo o status como AUTHORIZED por padrão
        transaction.setStatus(TransactionStatus.AUTHORIZED);

        return transaction;
    }

    public PaymentMethodType convertPaymentMethodType(String type) {
        try {
            return PaymentMethodType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de pagamento inválido: " + type, e);
        }
    }

    public TransactionResponse converteToDto(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setCardNumber(transaction.getMaskedCardNumber());
        response.setId(transaction.getTransactionId());

        DescriptionResponse descriptionResponse = getDescriptionResponse(transaction);

        PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse();
        paymentMethodResponse.setType(transaction.getType() != null ? transaction.getType().name() : "UNKNOWN");
        paymentMethodResponse.setInstallments(transaction.getInstallments() != null ? transaction.getInstallments() : 0);

        response.setDescriptionRequest(descriptionResponse);
        response.setPaymentMethodResponse(paymentMethodResponse);

        return response;
    }

    static DescriptionResponse getDescriptionResponse(Transaction transaction) {
        DescriptionResponse descriptionResponse = new DescriptionResponse();
        descriptionResponse.setAmount(transaction.getAmount());
        descriptionResponse.setDateTime(transaction.getDateTime());
        descriptionResponse.setEstablishment(transaction.getEstablishment());
        descriptionResponse.setNsu(String.valueOf(transaction.getNsu()));
        descriptionResponse.setAuthorizationCode(transaction.getAuthorizationCode());

        // Defina o status como "AUTHORIZED" por padrão se for nulo
        descriptionResponse.setStatus(transaction.getStatus() != null ? transaction.getStatus().name() : TransactionStatus.AUTHORIZED.name());
        return descriptionResponse;
    }

    public List<TransactionResponse> toDtoList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::converteToDto)
                .collect(Collectors.toList());
    }

    public TransactionStatus convertTransactionStatus(String status) {
        try {
            return TransactionStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status de transação inválido: " + status, e);
        }
    }
}