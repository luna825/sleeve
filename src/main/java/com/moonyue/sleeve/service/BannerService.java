package com.moonyue.sleeve.service;

import com.moonyue.sleeve.model.Banner;
import com.moonyue.sleeve.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public Banner getByName(String name){
        return bannerRepository.findOneByName(name);
    }
}
