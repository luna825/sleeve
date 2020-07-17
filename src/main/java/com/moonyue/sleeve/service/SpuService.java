package com.moonyue.sleeve.service;

import com.moonyue.sleeve.model.Spu;
import com.moonyue.sleeve.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpuService {

    @Autowired
    private SpuRepository spuRepository;

    public Optional<Spu> getById(Long id){
        return spuRepository.findOneById(id);
    }

    public List<Spu> getLatestPagingSpu(){
        return this.spuRepository.findAll();
    }
}
