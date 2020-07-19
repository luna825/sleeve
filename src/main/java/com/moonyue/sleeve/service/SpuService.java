package com.moonyue.sleeve.service;

import com.moonyue.sleeve.model.Spu;
import com.moonyue.sleeve.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpuService {

    @Autowired
    private SpuRepository spuRepository;

    public Optional<Spu> getById(Long id){
        return spuRepository.findOneById(id);
    }

    public Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size){
        Pageable page = PageRequest.of(pageNum, size, Sort.by("createTime").descending());
        return this.spuRepository.findAll(page);
    }

    public Page<Spu> getByCategoryIdWithPaging(Long cid, Boolean isRoot ,Integer pageNum, Integer size){
        Pageable page = PageRequest.of(pageNum, size);
        if(isRoot){
            return this.spuRepository.findByRootCategoryId(cid, page);
        }else{
            return this.spuRepository.findByCategoryIdOrderByCreateTime(cid, page);
        }
    }
}
