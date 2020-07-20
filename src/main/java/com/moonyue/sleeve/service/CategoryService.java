package com.moonyue.sleeve.service;

import com.moonyue.sleeve.model.Category;
import com.moonyue.sleeve.model.GridCategory;
import com.moonyue.sleeve.repository.CategoryRepository;
import com.moonyue.sleeve.repository.GridCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GridCategoryRepository gridCategoryRepository;

    public Map<Integer, List<Category>> getAll(){
        List<Category> roots = categoryRepository.findAllByIsRootOrderByIndexAsc(true);
        List<Category> subs = categoryRepository.findAllByIsRootOrderByIndexAsc(false);

        Map<Integer, List<Category>> map = new HashMap<>();
        map.put(1, roots);
        map.put(2, subs);
        return map;
    }

    public List<GridCategory> getGridCategory(){
        return this.gridCategoryRepository.findAll();
    }
}
