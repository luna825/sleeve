package com.moonyue.sleeve.vo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PagingDozer<T, K> extends Paging {

    public PagingDozer(Page<T> pageT, Class<K> kClass){
        this.initPageParameters(pageT);
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<K> vos = pageT.getContent().stream().map(t ->{
            K vo = mapper.map(t, kClass);
            return vo;
        }).collect(Collectors.toList());
        this.setItems(vos);
    }
}
