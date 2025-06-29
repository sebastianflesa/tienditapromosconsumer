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
    @JsonProperty("productoId")
    private Integer productoId;
    
    @JsonProperty("porcentajeDescuento")
    private Integer porcentajeDescuento;
    
    @JsonProperty("descripcion")
    private String descripcion;
   

}
