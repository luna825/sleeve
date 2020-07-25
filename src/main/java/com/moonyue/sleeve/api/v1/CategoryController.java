package com.moonyue.sleeve.api.v1;

import com.moonyue.sleeve.core.annotations.ScopeLevel;
import com.moonyue.sleeve.core.exception.NotFoundException;
import com.moonyue.sleeve.model.Category;
import com.moonyue.sleeve.model.GridCategory;
import com.moonyue.sleeve.service.CategoryService;
import com.moonyue.sleeve.vo.CategoryAllVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public CategoryAllVO getAll(){
        Map<Integer, List<Category>> map = categoryService.getAll();
        return new CategoryAllVO(map);
    }

    @GetMapping("/grid/all")
    public List<GridCategory> getGridCategoryList(){
        List<GridCategory> gridCategoryList = categoryService.getGridCategory();
        if (gridCategoryList.isEmpty()) {
            throw new NotFoundException(30009);
        }
        return gridCategoryList;
    }
}
