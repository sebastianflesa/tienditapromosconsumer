package com.tiendita.tienditapromosconsumer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendita.tienditapromosconsumer.dto.PromoDTO;
import com.tiendita.tienditapromosconsumer.models.Promocion;
import com.tiendita.tienditapromosconsumer.models.Producto;
import com.tiendita.tienditapromosconsumer.repository.PromocionRepository;
import com.tiendita.tienditapromosconsumer.repository.ProductoRepository;
import com.tiendita.tienditapromosconsumer.service.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class PromocionServiceImpl implements PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;
    
    @Autowired
    private ProductoRepository productoRepository;


    private final ObjectMapper objectMapper;

    public PromocionServiceImpl() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Promocion guardarPromocion(PromoDTO promoDTO) {
        try {
            Promocion promocion = new Promocion();
            promocion.setProductoId(promoDTO.getProductoId().longValue());
            promocion.setDescripcion(promoDTO.getDescripcion());            
            // Establecer fecha de vencimiento (por ejemplo, 30 días desde hoy)
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 30);
            promocion.setFechaVencimiento(LocalDate.now().plusDays(30));
            
            // Establecer como válida por defecto
            promocion.setValido(1);

            return promocionRepository.save(promocion);
        } catch (Exception e) {
            System.err.println("Error al guardar promoción en base de datos: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar promoción", e);
        }
    }

    @Override
    @Transactional
    public Promocion crearPromocionProductoMayorStock() {
        try {
            // Obtener el producto con mayor stock
            PageRequest pageRequest = PageRequest.of(0, 1);
            List<Producto> productos = productoRepository.findProductoConMayorStock(pageRequest);
            
            if (productos.isEmpty()) {
                throw new RuntimeException("No hay productos disponibles");
            }
            
            Producto producto = productos.get(0);
            
            // Generar descuento aleatorio entre 10% y 90%
            double descuentoAleatorio = ThreadLocalRandom.current().nextDouble(0.10, 0.91);
            int porcentajeDescuento = (int) Math.round(descuentoAleatorio * 100);
            
            // Crear la promoción
            Promocion promocion = new Promocion();
            promocion.setProductoId(producto.getId());
            promocion.setDescuento((double) porcentajeDescuento);
            promocion.setDescripcion("PROMO DE " + porcentajeDescuento + "% PARA " + producto.getNombre() + " CON MAYOR STOCK (" + producto.getStock() + ")");
            
            // Establecer fecha de vencimiento (30 días desde hoy)
            promocion.setFechaVencimiento(LocalDate.now().plusDays(30));
            
            // Establecer como válida
            promocion.setValido(1);

            
            return promocionRepository.save(promocion);

            /*
             * PageRequest pageRequest = PageRequest.of(0, 1);
            Optional<Producto> productoOpt = productoRepository.findProductoConMayorStock(pageRequest).stream().findFirst();
            Producto producto = productoOpt.get();
            Promocion promocion = new Promocion();
            double descuentoAleatorio = ThreadLocalRandom.current().nextDouble(0.10, 0.91);
            promocion.setProductoId(producto.getId());
            promocion.setDescripcion("PROMO DE " + Math.round(descuentoAleatorio*100) + "% PARA " + producto.getNombre() + " CON MAYOR STOCK" + " (" + producto.getStock() + ")");
            promocion.setDescuento((double) Math.round(descuentoAleatorio*100));
            promocion.setFechaVencimiento(LocalDate.now().plusDays(30));
            promocion.setValido(1);
            promocionRepository.save(promocion);
            return promocion;
             */
            
        } catch (Exception e) {
            System.err.println("Error al crear promoción para producto con mayor stock: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al crear promoción: " + e.getMessage());
        }
    }

    @Override
    public Optional<Promocion> obtenerUltimaPromocion() {
        try {
            return promocionRepository.findTopByOrderByIdDesc();
        } catch (Exception e) {
            System.err.println("Error al obtener la última promoción: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
