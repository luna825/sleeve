package com.moonyue.sleeve.repository;

import com.moonyue.sleeve.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    Banner findOneByName(String name);
}
