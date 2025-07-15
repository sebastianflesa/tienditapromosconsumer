package com.tiendita.tienditapromosconsumer.repository;

import com.tiendita.tienditapromosconsumer.models.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    
    Optional<Promocion> findTopByOrderByIdDesc();
    
}
