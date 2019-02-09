package io.luan.jerry.category;

import io.luan.jerry.category.data.CategoryMapper;
import io.luan.jerry.category.domain.Category;
import io.luan.jerry.category.domain.PropertyType;
import io.luan.jerry.category.repository.CategoryRepository;
import io.luan.jerry.category.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CategoryPropertyTests {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void createThenQueryWithPV() {
        var name = "Category" + System.currentTimeMillis();
        var cat = new Category();
        cat.setName(name);
        cat.setIsLeaf(true);

        // Prop
        var prop1 = cat.addProperty(1L, "prop1");
        var prop2 = cat.addProperty(2L, "prop2");

        // values
        var value1 = prop1.addValue(11L, "value1");
        var value2 = prop1.addValue(12L, "value2");
        var value3 = prop2.addValue(13L, "value3");
        var value4 = prop2.addValue(14L, "value4");

        categoryService.save(cat);
        assertNotNull(cat.getId());

        var fromDb = categoryRepository.findById(cat.getId(), true);
        assertNotNull(fromDb);

        System.out.println(fromDb);
        System.out.println(cat);

        assertEquals(fromDb, cat);
    }

    @Test
    public void createThenAdd() {
        var name = "Category" + System.currentTimeMillis();
        var cat = new Category();
        cat.setName(name);
        cat.setIsLeaf(true);

        System.out.println(cat);

        // First save
        categoryRepository.save(cat);
        assertNotNull(cat.getId());


        // Prop
        var prop1 = cat.addProperty(1L, "prop1");
        prop1.setSortOrder(1L);
        prop1.setPropertyType(PropertyType.Key);
        var prop2 = cat.addProperty(2L, "prop2");
        prop2.setSortOrder(2L);
        prop2.setPropertyType(PropertyType.Sale);
        System.out.println(prop1);

        categoryRepository.save(cat);

        // values
        var value1 = prop1.addValue(11L, "value1");
        value1.setSortOrder(1L);
        var value2 = prop1.addValue(12L, "value2");
        value2.setSortOrder(2L);
        var value3 = prop2.addValue(13L, "value3");
        value3.setSortOrder(3L);
        var value4 = prop2.addValue(14L, "value4");
        value4.setSortOrder(4L);

        categoryService.save(cat);


        var fromDb = categoryRepository.findById(cat.getId(), true);
        assertNotNull(fromDb);

        System.out.println(fromDb);
        System.out.println(cat);

        assertEquals(fromDb, cat);
    }
}
