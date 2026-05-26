package org.example.ptit_cntt1_it211_session10_ex3.service;

import org.example.ptit_cntt1_it211_session10_ex3.dto.CreateKeeperRequest;
import org.example.ptit_cntt1_it211_session10_ex3.entity.WarehouseKeeper;
import org.example.ptit_cntt1_it211_session10_ex3.repository.WarehouseKeeperRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WarehouseKeeperService {
    private final WarehouseKeeperRepository warehouseKeeperRepository;

    public WarehouseKeeperService(WarehouseKeeperRepository warehouseKeeperRepository) {
        this.warehouseKeeperRepository = warehouseKeeperRepository;
    }

    @Transactional
    public WarehouseKeeper create(CreateKeeperRequest request) {
        if (request.fullName() == null || request.fullName().isBlank()) {
            throw new IllegalArgumentException("Tên nhân viên kho không được để trống.");
        }
        if (request.staffCode() == null || request.staffCode().isBlank()) {
            throw new IllegalArgumentException("Mã nhân viên kho không được để trống.");
        }
        WarehouseKeeper keeper = new WarehouseKeeper();
        keeper.setFullName(request.fullName());
        keeper.setStaffCode(request.staffCode());
        return warehouseKeeperRepository.save(keeper);
    }
}
