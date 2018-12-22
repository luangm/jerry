package io.luan.jerry.user.service.impl;

import io.luan.jerry.user.domain.User;
import io.luan.jerry.user.repository.UserRepository;
import io.luan.jerry.user.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByNick(String nick) {
        return userRepository.findByNick(nick);
    }

    @Override
    public User create(String nick) {
        var user = new User();
        user.setNick(nick);

        return userRepository.save(user);
    }
}
