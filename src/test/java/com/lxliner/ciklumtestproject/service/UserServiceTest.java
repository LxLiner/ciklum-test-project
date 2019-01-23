package com.lxliner.ciklumtestproject.service;

import com.lxliner.ciklumtestproject.domain.User;
import com.lxliner.ciklumtestproject.exceptions.LoginIsEmptyException;
import com.lxliner.ciklumtestproject.exceptions.UserAlreadyExistsException;
import com.lxliner.ciklumtestproject.exceptions.WeakPasswordException;
import com.lxliner.ciklumtestproject.repository.impl.UserRepositoryImpl;
import com.lxliner.ciklumtestproject.service.impl.UserServiceImpl;
import com.lxliner.ciklumtestproject.util.PasswordStrengthChecker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    @Mock
    private UserRepositoryImpl userRepository;

    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;


    private User user = new User("user", "zxcASD123!@#");

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        userService.setPasswordEncoder(passwordEncoder);
        userService.setPasswordStrengthChecker(new PasswordStrengthChecker());
        Mockito.when(userRepository.findByLogin("registeredUser")).thenReturn(new User("registeredUser", "zxcASD123!@#"));
        Mockito.when(userRepository.findByLogin("notRegisteredUser")).thenReturn(null);
        Mockito.when(userRepository.save(user)).then((Answer) invocationOnMock -> {
            User user = (User) invocationOnMock.getArguments()[0];
            user.setId(1L);
            return null;
        }).thenReturn(user);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testAddUserUserAlreadyExistsException() throws UserAlreadyExistsException, WeakPasswordException, LoginIsEmptyException {
        userService.addUser(new User("registeredUser", "zxcASD123!@#"));
    }

    @Test(expected = WeakPasswordException.class)
    public void testAddUserWeakPasswordException() throws WeakPasswordException, UserAlreadyExistsException, LoginIsEmptyException {
        userService.addUser(new User("nonRegisteredUser", "123456"));
    }

    @Test(expected = LoginIsEmptyException.class)
    public void testAddUserLoginIsEmptyException() throws WeakPasswordException, UserAlreadyExistsException, LoginIsEmptyException {
        userService.addUser(new User("", "zxcASD123!@#"));
    }

    @Test
    public void testAddUser() throws WeakPasswordException, UserAlreadyExistsException, LoginIsEmptyException {
        String newPassword = user.getPassword();
        userService.addUser(user);

        Assert.assertNotNull("Expected not null id", user.getId());
        Assert.assertTrue("Expected equal encrypted passwords", passwordEncoder.matches(newPassword, user.getPassword()));
    }
}
