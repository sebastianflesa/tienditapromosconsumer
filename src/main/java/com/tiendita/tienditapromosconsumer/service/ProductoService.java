package com.tiendita.tienditapromosconsumer.service;

import com.tiendita.tienditapromosconsumer.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    
    List<Producto> obtenerTodosLosProductos();
    
    Optional<Producto> obtenerProductoPorId(Long id);
    
    Producto guardarProducto(Producto producto);
    
    boolean actualizarStock(Long productoId, Integer nuevoStock);
    
    boolean reducirStock(Long productoId, Integer cantidad);
    
    void eliminarProducto(Long id);
}
