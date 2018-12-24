package io.luan.jerry.user.repository.impl;

import io.luan.jerry.user.data.UserDO;
import io.luan.jerry.user.data.UserMapper;
import io.luan.jerry.user.domain.User;
import io.luan.jerry.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    @Autowired
    public UserRepositoryImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean delete(User user) {
        var userDO = UserDO.fromEntity(user);
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
            return userDO.toEntity();
        }
        return null;
    }

    @Override
    public User save(User user) {
        var userDO = UserDO.fromEntity(user);
        if (userDO.getId() == null) {
            userMapper.insert(userDO);
            return findById(userDO.getId());
        } else {
            return user;
        }
    }

    @Override
    public User findByUsername(String username) {
        var userDO = userMapper.findByUsername(username);
        if (userDO != null) {
            return userDO.toEntity();
        }
        return null;
    }

}
