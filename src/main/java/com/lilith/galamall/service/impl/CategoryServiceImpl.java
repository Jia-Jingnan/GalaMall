package com.lilith.galamall.service.impl;

import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.dao.CategoryMapper;
import com.lilith.galamall.entity.Category;
import com.lilith.galamall.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @Author:JiaJingnan
 * @Date: 上午9:54 2021/6/5
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public GalaRes<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)){
            log.info("未找到当前分类的子分类");
        }
        return GalaRes.createBySuccess(categoryList);
    }

    @Override
    public GalaRes updateCategoryName(Integer categoryId, String categoryName) {

        if (categoryId == null && StringUtils.isBlank(categoryName)){
            return GalaRes.createByErrorMessage("更新分类参数错误");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int count = categoryMapper.updateByPrimaryKey(category);
        if (count > 0){
            return GalaRes.createBySuccess("更新分类成功");
        }

        return GalaRes.createByErrorMessage("更新分类失败");
    }

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
