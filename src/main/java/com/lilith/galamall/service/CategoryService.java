package com.lilith.galamall.service;

import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.entity.Category;

import java.util.List;

/**
 * @Author:JiaJingnan
 * @Date: 上午9:53 2021/6/5
 */
public interface CategoryService {

    GalaRes addCategory(String categoryName, Integer parentId);

    GalaRes updateCategoryName(Integer categoryId, String categoryName);

    GalaRes<List<Category>> getChildrenParallelCategory(Integer categoryId);
}
