package com.moonyue.sleeve.vo;

import com.moonyue.sleeve.model.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class CategoryPureVO {
    private Long id;
    private String name;
    private Boolean isRoot;
    private Long parentId;
    private Long index;

    public CategoryPureVO(Category category){
        BeanUtils.copyProperties(category, this);
    }
}
