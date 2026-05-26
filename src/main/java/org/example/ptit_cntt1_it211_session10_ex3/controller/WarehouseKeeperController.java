package org.example.ptit_cntt1_it211_session10_ex3.controller;

import org.example.ptit_cntt1_it211_session10_ex3.dto.CreateKeeperRequest;
import org.example.ptit_cntt1_it211_session10_ex3.entity.WarehouseKeeper;
import org.example.ptit_cntt1_it211_session10_ex3.service.WarehouseKeeperService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keepers")
public class WarehouseKeeperController {
    private final WarehouseKeeperService warehouseKeeperService;

    public WarehouseKeeperController(WarehouseKeeperService warehouseKeeperService) {
        this.warehouseKeeperService = warehouseKeeperService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseKeeper create(@RequestBody CreateKeeperRequest request) {
        return warehouseKeeperService.create(request);
    }
}
