package com.tiendita.tienditapromosconsumer.service.impl;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

import com.tiendita.tienditapromosconsumer.config.RabbitMQConfig;
import com.tiendita.tienditapromosconsumer.dto.PromoDTO;
import com.tiendita.tienditapromosconsumer.service.MensajeService;
import com.tiendita.tienditapromosconsumer.service.JsonFileService;
import com.tiendita.tienditapromosconsumer.service.PromocionService;
import com.tiendita.tienditapromosconsumer.models.Promocion;

@Service
public class MensajeServiceImpl implements MensajeService {

    private final ObjectMapper objectMapper;
    
    @Autowired
    private JsonFileService jsonFileService;
    
    @Autowired
    private PromocionService promocionService;

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
            
            // Independientemente del contenido del mensaje, crear promoción para producto con mayor stock
            try {
                Promocion promocionGenerada = promocionService.crearPromocionProductoMayorStock();
                //System.out.println("Promoción generada automáticamente: " + promocionGenerada);
                
                // También guardar en archivo JSON si se requiere mantener funcionalidad existente
                //PromoDTO promoDTO = new PromoDTO();
                //promoDTO.setProductoId(promocionGenerada.getProductoId());
                //promoDTO.setDescuento((double) promocionGenerada.getDescuento().intValue());
                //promoDTO.setDescripcion(promocionGenerada.getDescripcion());
                
                //jsonFileService.savePromoToJsonFile(promoDTO);
                //System.out.println("Promoción también guardada en archivo JSON");
                
            } catch (Exception e) {
                System.err.println("Error al generar promoción automática: " + e.getMessage());
                e.printStackTrace();
            }
            
            // Intentar procesar el mensaje original como antes (por compatibilidad)
            try {
                String jsonLimpio = mensajeJson.trim();
                
                // Si el mensaje viene con dobles comillas, las removemos
                //if (jsonLimpio.startsWith("\"") && jsonLimpio.endsWith("\"")) {
                //    jsonLimpio = objectMapper.readValue(jsonLimpio, String.class);
                //}
                
                //PromoDTO promo = objectMapper.readValue(jsonLimpio, PromoDTO.class);
                
                // Guardar en base de datos
                //Promocion promocionGuardada = promocionService.guardarPromocion(promo);
                //System.out.println("Promoción del mensaje original guardada en base de datos: " + promocionGuardada);
                
            } catch (Exception e) {
                System.out.println("No se pudo parsear como PromoDTO (procesamiento normal): " + e.getMessage());
                // No es un error crítico ya que la promoción automática ya se generó
            }
            
        } catch (Exception e) {
            System.err.println("Error procesando mensaje de promos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
