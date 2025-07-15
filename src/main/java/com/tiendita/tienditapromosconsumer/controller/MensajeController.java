package com.tiendita.tienditapromosconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendita.tienditapromosconsumer.dto.StockUpdateDTO;
import com.tiendita.tienditapromosconsumer.dto.PromoDTO;
import com.tiendita.tienditapromosconsumer.models.Promocion;
import com.tiendita.tienditapromosconsumer.service.MensajeService;
import com.tiendita.tienditapromosconsumer.service.PromocionService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.tiendita.tienditapromosconsumer.config.RabbitMQConfig;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MensajeController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PromocionService promocionService;

    private final MensajeService mensajeService;
    private final ObjectMapper objectMapper;
    
    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping("")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Microservicio Promos Consumer - Conectado a cola de /promos RabbitMQ");
    }

    @GetMapping("/promociones")
    public ResponseEntity<?> obtenerUltimaPromocion() {
        try {
            Optional<Promocion> ultimaPromocion = promocionService.obtenerUltimaPromocion();
            
            if (ultimaPromocion.isPresent()) {
                return ResponseEntity.ok(ultimaPromocion.get());
            } else {
                return ResponseEntity.ok("No hay promociones disponibles");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error al obtener la última promoción: " + e.getMessage());
        }
    }

}
