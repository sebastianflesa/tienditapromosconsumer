package com.tiendita.tienditapromosconsumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PromoDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("productoId")
    private Long productoId;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("descuento")
    private Double descuento;

    @JsonProperty("fechaVencimiento")
    private java.time.LocalDate fechaVencimiento;

    @JsonProperty("valido")
    private Integer valido = 1;
}
