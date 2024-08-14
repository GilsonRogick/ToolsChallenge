package com.tools.payment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "cartao", "id", "descricao", "formaPagamento" })
public class TransactionResponse {

    @JsonProperty("cartao")
    private String cardNumber;

    private String id;

    @JsonProperty("descricao")
    private DescriptionResponse descriptionRequest;

    @JsonProperty("formaPagamento")
    private PaymentMethodResponse paymentMethodResponse;
}
