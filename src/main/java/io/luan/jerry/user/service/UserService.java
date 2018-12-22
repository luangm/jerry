package io.luan.jerry.user.service;

import io.luan.jerry.user.domain.User;

public interface UserService {

    User findById(Long id);

    User findByNick(String nick);

    User create(String nick);
}
