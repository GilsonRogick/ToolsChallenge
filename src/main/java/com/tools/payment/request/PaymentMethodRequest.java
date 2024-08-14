package com.tools.payment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PaymentMethodRequest {

    @NotBlank(message = "O campo TIPO não pode estar em branco!")
    @JsonProperty("tipo")
    private String type; // Recebe como String para compatibilidade com JSON

    @JsonProperty("parcelas")
    private Integer installments;
}
