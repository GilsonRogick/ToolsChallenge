package com.tools.payment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Transaction {

    @Id
    private String transactionId;

    private String cardNumber;

    private BigDecimal amount;

    private LocalDateTime dateTime;

    private String establishment;

    private Long nsu;

    private String authorizationCode;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethodType type;

    private Integer installments;


    public String getMaskedCardNumber() {
        return cardNumber
                .replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "*");
    }

    @PrePersist
    public void generateAuthorizationCode() {
        if (authorizationCode == null) {
            int randomNum = (int) (Math.random() * 900000000) + 100000000;
            authorizationCode = String.valueOf(randomNum);
        }
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
