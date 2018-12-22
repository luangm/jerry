package io.luan.jerry.user.repository;

import io.luan.jerry.common.repository.Repository;
import io.luan.jerry.user.domain.User;

public interface UserRepository extends Repository<User, Long> {

    User findByNick(String nick);

}
