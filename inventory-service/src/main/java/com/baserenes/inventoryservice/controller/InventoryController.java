package com.baserenes.inventoryservice.controller;

import com.baserenes.inventoryservice.service.InventoryService;
import com.baserenes.inventoryservice.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;//cons injection
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String>  skuCode){
        return inventoryService.isInStock(skuCode);




    }
}
