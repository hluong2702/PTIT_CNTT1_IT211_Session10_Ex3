package org.example.ptit_cntt1_it211_session10_ex3.repository;

import java.util.List;
import java.util.Optional;
import org.example.ptit_cntt1_it211_session10_ex3.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);

    List<Product> findByQuantityLessThan(Long threshold);

    @Modifying
    @Query("update Product p set p.quantity = p.quantity - :quantity where p.sku = :sku and p.quantity >= :quantity")
    int exportBySku(@Param("sku") String sku, @Param("quantity") Long quantity);
}
