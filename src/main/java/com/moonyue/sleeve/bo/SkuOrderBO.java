package com.moonyue.sleeve.bo;

import com.moonyue.sleeve.dto.SkuInfoDTO;
import com.moonyue.sleeve.model.Sku;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SkuOrderBO {

    private BigDecimal price;

    private Integer count;

    private Long categoryId;

    public SkuOrderBO(Sku sku, SkuInfoDTO skuInfoDTO){
        this.price = sku.getActualPrice();

        this.count = skuInfoDTO.getCount();

        this.categoryId = sku.getCategoryId();
    }

    public BigDecimal getTotalPrice(){
        return price.multiply(new BigDecimal(count));
    }
}
