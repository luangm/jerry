package io.luan.jerry.user.repository.impl;

import io.luan.jerry.user.data.UserDO;
import io.luan.jerry.user.data.UserMapper;
import io.luan.jerry.user.domain.User;
import io.luan.jerry.user.factory.UserFactory;
import io.luan.jerry.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserFactory userFactory;

    private final UserMapper userMapper;

    @Autowired
    public UserRepositoryImpl(UserMapper userMapper, UserFactory userFactory) {
        this.userMapper = userMapper;
        this.userFactory = userFactory;
    }

    @Override
    public boolean delete(User user) {
        var userDO = new UserDO(user);
        if (userDO.getId() != null) {
            int count = userMapper.delete(userDO);
            return count > 0;
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findById(Long id) {
        var userDO = userMapper.findById(id);
        if (userDO != null) {
            return userFactory.loadFromDataObject(userDO);
        }
        return null;
    }

    @Override
    public void save(User user) {
        var userDO = new UserDO(user);
        if (userDO.getId() == null) {
            userMapper.insert(userDO);
            user.setId(userDO.getId());
        }
    }

    @Override
    public User findByUsername(String username) {
        var userDO = userMapper.findByUsername(username);
        if (userDO != null) {
            return userFactory.loadFromDataObject(userDO);
        }
        return null;
    }

}
