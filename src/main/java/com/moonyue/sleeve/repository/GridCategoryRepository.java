package com.moonyue.sleeve.repository;

import com.moonyue.sleeve.model.GridCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GridCategoryRepository extends JpaRepository<GridCategory, Long> {
    List<GridCategory> findAll();
}
