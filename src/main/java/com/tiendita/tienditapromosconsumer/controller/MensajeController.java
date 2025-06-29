package com.tiendita.tienditapromosconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendita.tienditapromosconsumer.dto.StockUpdateDTO;
import com.tiendita.tienditapromosconsumer.dto.PromoDTO;
import com.tiendita.tienditapromosconsumer.service.MensajeService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.tiendita.tienditapromosconsumer.config.RabbitMQConfig;

@RestController
@RequestMapping("/api")
public class MensajeController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

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


}
