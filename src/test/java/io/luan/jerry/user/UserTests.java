package io.luan.jerry.user;

import io.luan.jerry.user.dto.UserRegistrationDTO;
import io.luan.jerry.user.repository.UserRepository;
import io.luan.jerry.user.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createThenQueryThenDelete() {
        var username = "User" + System.currentTimeMillis();
        var password = "Password";
        var badPassword = "BadPassword";

        var registrationDto = new UserRegistrationDTO();
        registrationDto.setUsername(username);
        registrationDto.setPassword(password);
        registrationDto.setConfirmPassword(password);

        var user = userService.register(registrationDto);
        Assert.assertNotNull(user);

        Long userId = user.getId();
        Assert.assertNotNull(userId);

        var userByUsername = userService.findByUsername(username);
        Assert.assertNotNull(userByUsername);
        Assert.assertEquals(username, userByUsername.getUsername());

        var userById = userService.findById(userId);
        Assert.assertNotNull(userById);
        Assert.assertEquals(username, userById.getUsername());

        var verifyPass = userById.verifyPassword(password);
        Assert.assertTrue(verifyPass);

        var verifyFail = userById.verifyPassword(badPassword);
        Assert.assertFalse(verifyFail);

        var success = userRepository.delete(user);
        Assert.assertTrue(success);

        var userAfterDelete = userService.findById(userId);
        Assert.assertNull(userAfterDelete);
    }

    @Test
    public void createTempUsers() {
        var username = "User1";
        var password = "Password";

        var registrationDto = new UserRegistrationDTO();
        registrationDto.setUsername(username);
        registrationDto.setPassword(password);
        registrationDto.setConfirmPassword(password);

        if (userService.findByUsername(username) == null) {
            var user = userService.register(registrationDto);
            Assert.assertNotNull(user);

            Assert.assertTrue(user.verifyPassword(password));

        }
    }
}
