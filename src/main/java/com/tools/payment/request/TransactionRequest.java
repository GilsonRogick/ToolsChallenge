package com.tools.payment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TransactionRequest {

    @JsonProperty("cartao")
    private String cardNumber;

    @JsonProperty("id")
    private String id;

    @NotNull(message = "O campo Descricao não pode estar em branco!")
    @JsonProperty("descricao")
    private DescriptionRequest descriptionRequest;

    @NotNull(message = "O campo formaPagamento não pode estar em branco!")
    @JsonProperty("formaPagamento")
    private PaymentMethodRequest paymentMethodRequest;
}
