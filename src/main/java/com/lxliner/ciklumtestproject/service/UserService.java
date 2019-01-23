package com.lxliner.ciklumtestproject.service;

import com.lxliner.ciklumtestproject.domain.User;
import com.lxliner.ciklumtestproject.exceptions.LoginIsEmptyException;
import com.lxliner.ciklumtestproject.exceptions.UserAlreadyExistsException;
import com.lxliner.ciklumtestproject.exceptions.WeakPasswordException;

public interface UserService {
    User addUser(User user) throws UserAlreadyExistsException, WeakPasswordException, LoginIsEmptyException;
    User findByLogin(String login);
}
