package io.luan.jerry.category;

import io.luan.jerry.category.data.BasePropertyMapper;
import io.luan.jerry.category.domain.BaseProperty;
import io.luan.jerry.category.repository.BasePropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PropertyTests {

    @Autowired
    private BasePropertyRepository propertyRepository;

    @Autowired
    private BasePropertyMapper propertyMapper;

    @Test
    public void createThenQueryThenDelete() {

        var name = "Property" + System.currentTimeMillis();

        var prop = new BaseProperty();
        prop.setName(name);

        propertyRepository.save(prop);
        assertNotNull(prop.getId());

        System.out.println(prop);

        var fromDb = propertyRepository.findById(prop.getId());
        assertNotNull(fromDb);
        assertEquals(name, fromDb.getName());

        var success = propertyRepository.delete(fromDb);
        assertTrue(success);

        var byIdAfterDelete = propertyRepository.findById(prop.getId());
        assertNull(byIdAfterDelete);
    }

}
