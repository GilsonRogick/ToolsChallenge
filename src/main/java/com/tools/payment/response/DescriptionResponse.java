package com.tools.payment.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class DescriptionResponse {

    @JsonProperty("valor")
    private BigDecimal amount;

    @JsonProperty("dataHora")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTime;

    @JsonProperty("estabelecimento")
    private String establishment;

    @JsonProperty("nsu")
    private String nsu;

    @JsonProperty("codigoAutorizacao")
    private String authorizationCode;

    @JsonProperty("status")
    private String status;
}