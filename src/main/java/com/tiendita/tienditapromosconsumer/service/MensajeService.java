package com.tiendita.tienditapromosconsumer.service;
import org.springframework.amqp.core.Message;
import com.rabbitmq.client.Channel;

public interface MensajeService {
    void recibirDeadLetter(Object mensaje);
    void recibirMensajePromos(Message mensaje, Channel canal);
}
