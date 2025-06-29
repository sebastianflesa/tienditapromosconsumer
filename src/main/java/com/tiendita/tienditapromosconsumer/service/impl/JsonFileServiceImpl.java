package com.tiendita.tienditapromosconsumer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tiendita.tienditapromosconsumer.dto.PromoDTO;
import com.tiendita.tienditapromosconsumer.service.JsonFileService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsonFileServiceImpl implements JsonFileService {

    private final ObjectMapper objectMapper;
    private static final String PROMOS_FILE_PATH = "promos_messages.json";

    public JsonFileServiceImpl() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void savePromoToJsonFile(PromoDTO promo) {
        try {
            File file = new File(PROMOS_FILE_PATH);
            List<ObjectNode> promosList = new ArrayList<>();

            // Si el archivo existe, leer contenido existente
            if (file.exists() && file.length() > 0) {
                ArrayNode existingPromos = (ArrayNode) objectMapper.readTree(file);
                for (int i = 0; i < existingPromos.size(); i++) {
                    promosList.add((ObjectNode) existingPromos.get(i));
                }
            }

            // Crear nuevo objeto con timestamp
            ObjectNode promoNode = objectMapper.createObjectNode();
            promoNode.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            promoNode.set("data", objectMapper.valueToTree(promo));

            promosList.add(promoNode);

            // Escribir al archivo
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, promosList);
            System.out.println("Promo guardada en archivo JSON: " + PROMOS_FILE_PATH);

        } catch (IOException e) {
            System.err.println("Error guardando promo en archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
}
