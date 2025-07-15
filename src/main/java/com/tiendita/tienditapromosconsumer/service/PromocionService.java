package com.tiendita.tienditapromosconsumer.service;

import com.tiendita.tienditapromosconsumer.dto.PromoDTO;
import com.tiendita.tienditapromosconsumer.models.Promocion;

import java.util.Optional;

public interface PromocionService {
    Promocion guardarPromocion(PromoDTO promoDTO);
    Promocion crearPromocionProductoMayorStock();
    Optional<Promocion> obtenerUltimaPromocion();
}
