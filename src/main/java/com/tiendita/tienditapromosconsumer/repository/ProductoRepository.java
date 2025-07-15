package com.tiendita.tienditapromosconsumer.repository;

import com.tiendita.tienditapromosconsumer.models.Producto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    @Query("SELECT p FROM Producto p ORDER BY p.stock DESC")
    List<Producto> findProductoConMayorStock(PageRequest pageRequest);
}
