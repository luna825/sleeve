package com.moonyue.sleeve.repository;

import com.moonyue.sleeve.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkuRepository extends JpaRepository<Sku, Long> {
    List<Sku> findAllByIdIn(List<Long> ids);

    @Modifying
    @Query("update Sku s set s.stock = s.stock - :quantity\n" +
            "where s.id = :sid\n" +
            "and s.stock >= :quantity")
    int reduceStock(Long sid, Long quantity);
}
