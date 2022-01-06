package com.kidd.msacatalogservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kidd.msacatalogservice.repository.CatalogEntity;
import com.kidd.msacatalogservice.repository.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {
    CatalogRepository catalogRepository;

    @Autowired
    public KafkaConsumer(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @KafkaListener(topics = "example_catalog_topic", groupId = "consumerGroupId")
    public void updateQty(String kafkaMessage) {
        log.info("Kafka Message : -> " + kafkaMessage);
        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException");
        }

        CatalogEntity entity = catalogRepository.findByProductId((String)map.get("productId"));
        if (entity != null) {
            entity.setStock(entity.getStock() - (Integer)map.get("qty"));
            catalogRepository.save(entity);
        }
    }
}
