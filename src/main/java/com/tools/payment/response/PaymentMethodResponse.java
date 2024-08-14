package com.tools.payment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tools.payment.model.PaymentMethodType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodResponse {

    @JsonProperty("tipo")
    private String type;

    @JsonProperty("parcelas")
    private Integer installments;
}
