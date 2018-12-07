package io.luan.jerry;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User register(String nick) {
        var user = new User();
        user.setNick(nick);
        userMapper.insert(user);

        return getById(user.getId());
    }

    public User getById(long id) {
        return userMapper.findById(id);
    }

    public User getByNick(String nick) {
        return userMapper.findByNick(nick);
    }
}
