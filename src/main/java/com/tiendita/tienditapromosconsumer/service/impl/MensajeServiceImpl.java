package com.tiendita.tienditapromosconsumer.service.impl;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.rabbitmq.client.Channel;

import com.tiendita.tienditapromosconsumer.config.RabbitMQConfig;
import com.tiendita.tienditapromosconsumer.dto.StockUpdateDTO;
import com.tiendita.tienditapromosconsumer.dto.PromoDTO;
import com.tiendita.tienditapromosconsumer.service.MensajeService;
import com.tiendita.tienditapromosconsumer.service.JsonFileService;

@Service
public class MensajeServiceImpl implements MensajeService {

    private final ObjectMapper objectMapper;
    
    @Autowired
    private JsonFileService jsonFileService;


    public MensajeServiceImpl() {
        this.objectMapper = new ObjectMapper();
    }


    @RabbitListener(id = "listener-dlx-queue", queues = RabbitMQConfig.DLX_QUEUE)
    @Override
    public void recibirDeadLetter(Object objeto) {
        System.out.println("Mensaje recibido en DLQ: " + objeto);
    }

    @RabbitListener(id = "listener-promos-queue", queues = RabbitMQConfig.PROMOS_QUEUE)
    @Override
    public void recibirMensajePromos(Message mensaje, Channel canal) {
        try {
            String mensajeJson = new String(mensaje.getBody());
            System.out.println("Mensaje recibido en cola promos: " + mensajeJson);
            
            try {
                String jsonLimpio = mensajeJson.trim();
                
                if (jsonLimpio.startsWith("\"") && jsonLimpio.endsWith("\"")) {
                    jsonLimpio = objectMapper.readValue(jsonLimpio, String.class);
                }
                
                PromoDTO promo = objectMapper.readValue(jsonLimpio, PromoDTO.class);
                jsonFileService.savePromoToJsonFile(promo);
                
                System.out.println("Promo procesada y guardada: " + promo);
                
            } catch (Exception e) {
                System.err.println("No se pudo parsear como PromoDTO, guardando como mensaje genérico: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("Error procesando mensaje de promos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
