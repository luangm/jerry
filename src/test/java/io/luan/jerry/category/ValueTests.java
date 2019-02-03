package io.luan.jerry.category;

import io.luan.jerry.category.data.BasePropertyMapper;
import io.luan.jerry.category.data.BaseValueMapper;
import io.luan.jerry.category.domain.BaseProperty;
import io.luan.jerry.category.domain.BaseValue;
import io.luan.jerry.category.repository.BasePropertyRepository;
import io.luan.jerry.category.repository.BaseValueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ValueTests {

    @Autowired
    private BaseValueRepository repository;

    @Autowired
    private BaseValueMapper mapper;

    @Test
    public void createThenQueryThenDelete() {

        var name = "Value" + System.currentTimeMillis();

        var value = new BaseValue();
        value.setValue(name);

        repository.save(value);
        assertNotNull(value.getId());

        System.out.println(value);

        var fromDb = repository.findById(value.getId());
        assertNotNull(fromDb);
        assertEquals(name, fromDb.getValue());

        var success = repository.delete(fromDb);
        assertTrue(success);

        var byIdAfterDelete = repository.findById(value.getId());
        assertNull(byIdAfterDelete);
    }

}
