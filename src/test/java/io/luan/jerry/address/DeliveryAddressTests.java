package io.luan.jerry.address;

import io.luan.jerry.address.data.DeliveryAddressDO;
import io.luan.jerry.address.data.DeliveryAddressMapper;
import io.luan.jerry.address.domain.Address;
import io.luan.jerry.address.domain.DeliveryAddress;
import io.luan.jerry.address.repository.DeliveryAddressRepository;
import io.luan.jerry.address.service.DeliveryAddressService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliveryAddressTests {

    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private DeliveryAddressService service;

    @Test
    public void mapperSave() {

        var address = new DeliveryAddress();
        address.setUserId(123L);
        address.setPhone("186-3231-3256");
        address.setAddress(new Address("ABC"));
        address.setReceiver("Tester");
        address.setIsDefault(false);


        var addressDO = new DeliveryAddressDO(address);

        deliveryAddressMapper.insert(addressDO);

        var fromDb = deliveryAddressMapper.findById(addressDO.getId());

        System.out.println(addressDO);
        System.out.println(fromDb);

        Assert.assertEquals(addressDO.getId(), fromDb.getId());
        Assert.assertEquals(addressDO.getGmtCreate(), fromDb.getGmtCreate());
        Assert.assertEquals(addressDO.getGmtModified(), fromDb.getGmtModified());
    }

    @Test
    public void repoDelete() {
        var address = new DeliveryAddress();
        address.setUserId(123L);
        address.setPhone("186-3231-3256");
        address.setAddress(new Address("ABC"));
        address.setReceiver("Receiver " + System.currentTimeMillis() / 1000);
        address.setIsDefault(false);

        System.out.println(address);

        deliveryAddressRepository.save(address);

        System.out.println(address);

        Assert.assertNotNull(address.getId());

        var success = deliveryAddressRepository.delete(address);

        var fromDb = deliveryAddressRepository.findById(address.getId());

        Assert.assertTrue(success);
        Assert.assertTrue(address.getIsDeleted());
        Assert.assertTrue(fromDb.getIsDeleted());
        System.out.println(address);
        System.out.println(fromDb);
    }

    @Test
    public void repoFindAllTest() {
        var address = new DeliveryAddress();
        address.setUserId(123L);
        address.setPhone("186-3231-3256");
        address.setAddress(new Address("ABC"));
        address.setReceiver("Receiver " + System.currentTimeMillis() / 1000);
        address.setIsDefault(false);

        deliveryAddressRepository.save(address);

        System.out.println(address);

        Assert.assertNotNull(address.getId());

        var listFromDb = deliveryAddressRepository.findAllByUserId(123L);

        System.out.println(listFromDb.size());

        Assert.assertTrue(listFromDb.size() > 0);
    }

    @Test
    public void repoTest() {
        var address = new DeliveryAddress();
        address.setUserId(123L);
        address.setPhone("186-3231-3256");
        address.setAddress(new Address("ABC"));
        address.setReceiver("Tester");
        address.setIsDefault(false);

        deliveryAddressRepository.save(address);

        System.out.println(address);

        Assert.assertNotNull(address.getId());

        var fromDb = deliveryAddressRepository.findById(address.getId());
        Assert.assertNotNull(fromDb);

        Assert.assertEquals(address.getId(), fromDb.getId());
        Assert.assertEquals(address.getUserId(), fromDb.getUserId());
        Assert.assertEquals(address.getAddress(), fromDb.getAddress());
        Assert.assertEquals(address.getIsDefault(), fromDb.getIsDefault());
        Assert.assertEquals(address.getIsDeleted(), fromDb.getIsDeleted());
        Assert.assertEquals(address.getPhone(), fromDb.getPhone());
        Assert.assertEquals(address.getReceiver(), fromDb.getReceiver());
        Assert.assertEquals(address.getGmtCreate(), fromDb.getGmtCreate());
        Assert.assertEquals(address.getGmtModified(), fromDb.getGmtModified());

    }

    @Test
    public void testService() throws InterruptedException {
        var address = new DeliveryAddress();
        address.setUserId(123L);
        address.setPhone("186-3231-3256");
        address.setAddress(new Address("ABC"));
        address.setReceiver("Tester");
        address.setIsDefault(false);

        service.save(address);
        Assert.assertNotNull(address.getId());
        System.out.println(address);

        address.setAddress(new Address("BCD"));
        service.save(address);

        var fromDb = service.findById(address.getId());
        Assert.assertNotNull(fromDb);

        Assert.assertEquals(address.getId(), fromDb.getId());
        Assert.assertEquals(address.getUserId(), fromDb.getUserId());
        Assert.assertEquals(address.getAddress(), fromDb.getAddress());
        Assert.assertEquals(address.getIsDefault(), fromDb.getIsDefault());
        Assert.assertEquals(address.getIsDeleted(), fromDb.getIsDeleted());
        Assert.assertEquals(address.getPhone(), fromDb.getPhone());
        Assert.assertEquals(address.getReceiver(), fromDb.getReceiver());
        Assert.assertEquals(address.getGmtCreate(), fromDb.getGmtCreate());
        Assert.assertEquals(address.getGmtModified(), fromDb.getGmtModified());

        Thread.sleep(1000);

        service.delete(address);

        Assert.assertTrue(address.getIsDeleted());
    }
}
