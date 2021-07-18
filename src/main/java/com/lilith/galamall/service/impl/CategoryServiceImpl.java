package com.lilith.galamall.service.impl;

import com.google.common.collect.Lists;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.dao.CategoryMapper;
import com.lilith.galamall.entity.Category;
import com.lilith.galamall.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @Author:JiaJingnan
 * @Date: 上午9:54 2021/6/5
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    // 递归，算出子节点
    private Set<Category> findChildrenCategory(Set<Category> categorySet,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        // 查找子节点,递归算法要有退出的条件
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryItem : categoryList) {
            findChildrenCategory(categorySet,categoryItem.getId());
        }

        return categorySet;
    }

    @Override
    public GalaRes<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet = newHashSet();
        findChildrenCategory(categorySet,categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null){
            for (Category categoryItem : categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }

        return GalaRes.createBySuccess(categoryIdList);
    }

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
