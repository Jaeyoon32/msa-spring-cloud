package com.kidd.msaorderservice.controller;

import com.kidd.msaorderservice.dto.OrderDto;
import com.kidd.msaorderservice.messagequeue.KafkaProducer;
import com.kidd.msaorderservice.messagequeue.OrderProducer;
import com.kidd.msaorderservice.repository.OrderEntity;
import com.kidd.msaorderservice.service.OrderService;
import com.kidd.msaorderservice.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/order-service")
public class OrderController {
    Environment env;
    OrderService orderService;
    KafkaProducer kafkaProducer;
    OrderProducer orderProducer;

    @Autowired
    public OrderController(Environment env, OrderService orderService,
                           KafkaProducer kafkaProducer, OrderProducer orderProducer) {
        this.env = env;
        this.orderService = orderService;
        this.kafkaProducer = kafkaProducer;
        this.orderProducer = orderProducer;
    }

    @GetMapping(value = "/health-check")
    public String status() {
        return String.format("It's working in Order service on Port %s", env.getProperty("local.server.port"));
    }

    @PostMapping(value = "/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody ResponseOrder order) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(order, OrderDto.class);
        orderDto.setUserId(userId);

        // JPA
        //OrderDto createdOrder = orderService.createOrder(orderDto);
        //ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

        // Kafka
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(order.getQty() * order.getUnitPrice());

        // send topic using Kafka
        kafkaProducer.send("example_catalog_topic", orderDto);
        orderProducer.send("orders", orderDto);

        ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping(value = "/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable("userId") String userId) {
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
