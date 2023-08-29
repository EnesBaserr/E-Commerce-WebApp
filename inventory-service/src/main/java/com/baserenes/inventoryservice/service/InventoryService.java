package com.baserenes.inventoryservice.service;

import com.baserenes.inventoryservice.dto.InventoryResponse;
import com.baserenes.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class InventoryService {

    private final InventoryRepository inventoryRepository;//cons injection.
    @Transactional(readOnly = true)
    @SneakyThrows //do not use in prod code. it will consume exception in theread.sleep
    public List<InventoryResponse> isInStock(List<String> skuCode){
        log.info("Wait started");

        log.info("Wait ended");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()>0)
                            .build()
                ).toList();


    }
}
