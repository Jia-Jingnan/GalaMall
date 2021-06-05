package com.lilith.galamall.service.impl;

import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.dao.CategoryMapper;
import com.lilith.galamall.entity.Category;
import com.lilith.galamall.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:JiaJingnan
 * @Date: 上午9:54 2021/6/5
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public GalaRes addCategory(String categoryName, Integer parentId) {
        if (parentId == null && StringUtils.isBlank(categoryName)){
            return GalaRes.createByErrorMessage("添加分类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int count = categoryMapper.insert(category);
        if (count > 0){
            return GalaRes.createBySuccess("添加分类成功");
        }

        return GalaRes.createByErrorMessage("添加分类失败");
    }
}
