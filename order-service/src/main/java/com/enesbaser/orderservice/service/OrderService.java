package com.enesbaser.orderservice.service;

import com.enesbaser.orderservice.dto.InventoryResponse;
import com.enesbaser.orderservice.dto.OrderLineItemsDto;
import com.enesbaser.orderservice.dto.OrderRequest;
import com.enesbaser.orderservice.event.OrderPlacedEvent;
import com.enesbaser.orderservice.model.Order;
import com.enesbaser.orderservice.model.OrderLineItems;
import com.enesbaser.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;//constructor injection.
    private final WebClient.Builder webClientBuilder ;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                .toList();
        order.setOrderLineItemsList(orderLineItems);
       List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(orderLineItems1 -> orderLineItems1.getSkuCode())
                .toList();
        //call inventory server wheter product is in stock or not.
        InventoryResponse[] inventoryResponsArray = webClientBuilder.build()
                        .get()
                        .uri("http://inventory-service/api/inventory",
                                uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes)
                                        .build())
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();
       boolean allProductsInStock = Arrays.stream(inventoryResponsArray)
               .allMatch(inventoryResponse -> inventoryResponse.isInStock());
        if (allProductsInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
            return "Order placed !!! ";
        } else {
            throw new IllegalArgumentException("Product is not in stock !!!");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;

    }
}
