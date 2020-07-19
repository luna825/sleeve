package com.moonyue.sleeve.repository;

import com.moonyue.sleeve.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpuRepository extends JpaRepository<Spu, Long> {
    Optional<Spu> findOneById(Long Id);

    Page<Spu> findByRootCategoryId(Long cid, Pageable pageable);

    Page<Spu> findByCategoryIdOrderByCreateTime(Long cid, Pageable pageable);
}
