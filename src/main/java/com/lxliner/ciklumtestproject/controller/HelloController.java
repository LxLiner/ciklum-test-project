package com.lxliner.ciklumtestproject.controller;

import com.lxliner.ciklumtestproject.domain.User;
import com.lxliner.ciklumtestproject.exceptions.LoginIsEmptyException;
import com.lxliner.ciklumtestproject.exceptions.UserAlreadyExistsException;
import com.lxliner.ciklumtestproject.exceptions.WeakPasswordException;
import com.lxliner.ciklumtestproject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

    private UserServiceImpl userService;

    @Autowired
    public HelloController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/helloworld")
    public String helloworld(@RequestParam(name="name", required = false, defaultValue = "World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "helloworld";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute(new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user) throws UserAlreadyExistsException, WeakPasswordException, LoginIsEmptyException {
        userService.addUser(user);
        return "redirect:/login";
    }

    @GetMapping("/passStrength")
    @ResponseBody
    public String getPasswordStrength(@RequestParam("password") String password) {
        return userService.getPasswordStrength(password);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleDataValidationException(HttpServletRequest request, UserAlreadyExistsException exception, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("err", exception.getMessage());
        return "redirect:/registration";
    }

    @ExceptionHandler(WeakPasswordException.class)
    public String handleDataValidationException(HttpServletRequest request, WeakPasswordException exception, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("err", exception.getMessage());
        return "redirect:/registration";
    }

    @ExceptionHandler(LoginIsEmptyException.class)
    public String handleDataValidationException(HttpServletRequest request, LoginIsEmptyException exception, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("err", exception.getMessage());
        return "redirect:/registration";
    }
}
