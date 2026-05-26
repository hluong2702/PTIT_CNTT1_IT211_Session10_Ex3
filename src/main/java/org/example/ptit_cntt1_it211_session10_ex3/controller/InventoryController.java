package org.example.ptit_cntt1_it211_session10_ex3.controller;

import java.util.List;
import org.example.ptit_cntt1_it211_session10_ex3.dto.InventoryActionRequest;
import org.example.ptit_cntt1_it211_session10_ex3.entity.Product;
import org.example.ptit_cntt1_it211_session10_ex3.service.InventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/import")
    public Product importStock(@RequestBody InventoryActionRequest request) {
        return inventoryService.importStock(request);
    }

    @PostMapping("/export")
    public Product exportStock(@RequestBody InventoryActionRequest request) {
        return inventoryService.exportStock(request);
    }

    @GetMapping("/low-stock")
    public List<Product> lowStock() {
        return inventoryService.lowStock();
    }
}
