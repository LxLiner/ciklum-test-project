package com.lxliner.ciklumtestproject.service.impl;

import com.lxliner.ciklumtestproject.domain.User;
import com.lxliner.ciklumtestproject.exceptions.LoginIsEmptyException;
import com.lxliner.ciklumtestproject.exceptions.UserAlreadyExistsException;
import com.lxliner.ciklumtestproject.exceptions.WeakPasswordException;
import com.lxliner.ciklumtestproject.repository.impl.UserRepositoryImpl;
import com.lxliner.ciklumtestproject.service.UserService;
import com.lxliner.ciklumtestproject.util.PasswordStrengthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private UserRepositoryImpl userRepository;
    private PasswordEncoder passwordEncoder;
    private PasswordStrengthChecker passwordStrengthChecker;

    @Autowired
    public UserServiceImpl(UserRepositoryImpl userRepository, PasswordEncoder passwordEncoder, PasswordStrengthChecker passwordStrengthChecker) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordStrengthChecker = passwordStrengthChecker;
    }

    public void setUserRepository(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setPasswordStrengthChecker(PasswordStrengthChecker passwordStrengthChecker) {
        this.passwordStrengthChecker = passwordStrengthChecker;
    }

    @Override
    public User addUser(User user) throws UserAlreadyExistsException, WeakPasswordException, LoginIsEmptyException {
        if(user.getLogin().length() == 0) {
            throw new LoginIsEmptyException("Login field is empty");
        }

        User userFromDb = findByLogin(user.getLogin());

        if (userFromDb != null) {
            throw new UserAlreadyExistsException("User " + user.getLogin() + " already exists");
        }
        if (passwordStrengthChecker.isWeakPassword(user.getPassword())) {
            throw new WeakPasswordException("Password is weak");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public String getPasswordStrength(String password) {
        return Integer.toString(passwordStrengthChecker.getPasswordStrength(password));
    }
}
