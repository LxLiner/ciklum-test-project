package com.lxliner.ciklumtestproject.repository;

import com.lxliner.ciklumtestproject.domain.User;

public interface UserRepository {
    User findByLogin(String login);
    User save(User user);
}
