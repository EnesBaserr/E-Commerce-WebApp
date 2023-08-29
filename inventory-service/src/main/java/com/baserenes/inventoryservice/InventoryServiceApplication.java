package com.baserenes.inventoryservice;

import com.baserenes.inventoryservice.model.Inventory;
import com.baserenes.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		//to create data in db for testing purpose

		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("Arsenal Jersey");
			inventory.setQuantity(100);
			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("Man City Jersey");
			inventory2.setQuantity(0);
			Inventory inventory3 = new Inventory();
			inventory3.setSkuCode("West Ham Jersey");
			inventory3.setQuantity(15);
			inventoryRepository.saveAll(List.of(inventory,inventory2,inventory3));

		};


	}

}
