package io.luan.jerry.user;

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
    public void createUserThenQueryThenDelete() {
        var nick = "User" + System.currentTimeMillis();
        var user = userService.create(nick);
        Assert.assertNotNull(user);
        Long userId = user.getId();
        Assert.assertNotNull(userId);

        var userByNick = userService.findByNick(nick);
        Assert.assertNotNull(userByNick);
        Assert.assertEquals(nick, userByNick.getNick());

        var userById = userService.findById(userId);
        Assert.assertNotNull(userById);
        Assert.assertEquals(nick, userById.getNick());

        var success = userRepository.delete(user);
        Assert.assertTrue(success);

        var userByNickAfterDelete = userService.findByNick(nick);
        Assert.assertNull(userByNickAfterDelete);
    }

}
