package io.luan.jerry.category;

import io.luan.jerry.category.data.CategoryMapper;
import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.repository.CategoryRepository;
import io.luan.jerry.category.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryTests {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void createThenQueryThenDelete() {

        var name = "Category" + System.currentTimeMillis();

        var category = new Category();
        category.setName(name);
        category.setParentId(9999L);
        category.setIsLeaf(true);

        categoryService.save(category);
        Assert.assertNotNull(category);

        Long categoryId = category.getId();
        Assert.assertNotNull(categoryId);

        var byId = categoryService.findById(categoryId);
        Assert.assertNotNull(byId);
        Assert.assertEquals(name, byId.getName());
        Assert.assertEquals(Long.valueOf(9999L), byId.getParentId());
        Assert.assertTrue(byId.getIsLeaf());

        var success = categoryRepository.delete(byId);
        Assert.assertTrue(success);

        var byIdAfterDelete = categoryService.findById(categoryId);
        Assert.assertNull(byIdAfterDelete);
    }

    @Test
    public void findAll() {
        var oldCount = categoryService.findAll().size();

        var name = "Category" + System.currentTimeMillis();
        var cat1 = new Category();
        cat1.setName(name);
        cat1.setIsLeaf(true);
        categoryService.save(cat1);

        var name2 = "Category" + System.currentTimeMillis();
        var cat2 = new Category();
        cat2.setName(name2);
        cat2.setIsLeaf(true);
        categoryService.save(cat2);

        var name3 = "Category" + System.currentTimeMillis();
        var cat3 = new Category();
        cat3.setName(name3);
        cat3.setIsLeaf(true);
        categoryService.save(cat3);

        var categories = categoryService.findAll();
        Assert.assertNotNull(categories);
        Assert.assertEquals(3 + oldCount, categories.size());

        categoryRepository.delete(cat1);
        categoryRepository.delete(cat2);
        categoryRepository.delete(cat3);
    }

}
