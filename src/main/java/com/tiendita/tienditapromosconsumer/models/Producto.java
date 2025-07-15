package com.tiendita.tienditapromosconsumer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCTOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "VALOR")
    private Integer valor;
    
    @Column(name = "STOCK")
    private Integer stock;
    
}
