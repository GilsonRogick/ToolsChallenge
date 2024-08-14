package com.tools.payment.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class DescriptionRequest {

    @NotNull(message = "O campo Valor não pode estar em branco!")
    @JsonProperty("valor")
    private BigDecimal amount;

    @NotNull(message = "O campo dataHora não pode estar em branco!")
    @JsonProperty("dataHora")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTime;

    @NotNull(message = "O campo estabelecimento não pode estar em branco!")
    @JsonProperty("estabelecimento")
    private String establishment;
}