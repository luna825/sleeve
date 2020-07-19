package com.moonyue.sleeve.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.moonyue.sleeve.common.util.GenericAndJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
public class Sku extends BaseEntity {
    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;
    private String specs;
    private String code;
    private Long stock;
    private Long categoryId;
    private Long rootCategoryId;


    public List<Spec> getSpecs() {
        if (this.specs == null){
            return Collections.emptyList();
        }
        return GenericAndJson.JsonToObject(this.specs, new TypeReference<List<Spec>>() {
        });
    }

    public void setSpecs(List<Spec> specs) {
        if (specs.isEmpty()){
            return;
        }
        this.specs = GenericAndJson.ObjectToJson(specs);
    }
}
