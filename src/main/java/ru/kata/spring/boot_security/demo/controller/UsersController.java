package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller

public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/user")
    public String userInfo(ModelMap model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getAuthorities());
        model.addAttribute("user", userService.show(user.getId()));
        return "user";
    }



}
