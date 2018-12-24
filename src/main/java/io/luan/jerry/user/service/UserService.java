package io.luan.jerry.user.service;

import io.luan.jerry.user.domain.User;
import io.luan.jerry.user.dto.UserRegistrationDTO;

public interface UserService {

    User findById(Long id);

    User findByUsername(String username);

    User register(UserRegistrationDTO request);
}
