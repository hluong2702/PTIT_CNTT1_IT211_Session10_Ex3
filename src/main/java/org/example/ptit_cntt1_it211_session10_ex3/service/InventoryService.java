package org.example.ptit_cntt1_it211_session10_ex3.service;

import java.util.List;
import org.example.ptit_cntt1_it211_session10_ex3.dto.InventoryActionRequest;
import org.example.ptit_cntt1_it211_session10_ex3.entity.Product;
import org.example.ptit_cntt1_it211_session10_ex3.entity.WarehouseKeeper;
import org.example.ptit_cntt1_it211_session10_ex3.repository.ProductRepository;
import org.example.ptit_cntt1_it211_session10_ex3.repository.WarehouseKeeperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
    private final ProductRepository productRepository;
    private final WarehouseKeeperRepository warehouseKeeperRepository;

    public InventoryService(ProductRepository productRepository, WarehouseKeeperRepository warehouseKeeperRepository) {
        this.productRepository = productRepository;
        this.warehouseKeeperRepository = warehouseKeeperRepository;
    }

    @Transactional
    public Product importStock(InventoryActionRequest request) {
        validateQuantity(request.quantity());
        WarehouseKeeper keeper = getKeeperOrThrow(request.keeperId());
        Product product = getProductOrThrow(request.sku());
        product.setQuantity(product.getQuantity() + request.quantity());
        Product updated = productRepository.save(product);
        log.info("Keeper {} imported {} units for SKU {}", keeper.getStaffCode(), request.quantity(), request.sku());
        return updated;
    }

    @Transactional
    public Product exportStock(InventoryActionRequest request) {
        validateQuantity(request.quantity());
        WarehouseKeeper keeper = getKeeperOrThrow(request.keeperId());
        Product existing = getProductOrThrow(request.sku());
        if (request.quantity() > existing.getQuantity()) {
            throw new IllegalArgumentException("Số lượng xuất hàng vượt quá tồn kho hiện tại!");
        }

        int updated = productRepository.exportBySku(request.sku(), request.quantity());
        if (updated == 0) {
            throw new IllegalArgumentException("Số lượng xuất hàng vượt quá tồn kho hiện tại!");
        }

        Product product = getProductOrThrow(request.sku());
        log.info("Keeper {} exported {} units for SKU {}", keeper.getStaffCode(), request.quantity(), request.sku());
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> lowStock() {
        return productRepository.findByQuantityLessThan(5L);
    }

    private WarehouseKeeper getKeeperOrThrow(Long keeperId) {
        return warehouseKeeperRepository.findById(keeperId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy mã nhân viên kho: " + keeperId));
    }

    private Product getProductOrThrow(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy mã SKU: " + sku));
    }

    private void validateQuantity(Long quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Số lượng thao tác phải lớn hơn 0.");
        }
    }
}
