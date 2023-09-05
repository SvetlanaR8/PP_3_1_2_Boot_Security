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
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UsersDetailsService;

import java.util.List;


@Controller

public class UsersController {
    private final UsersDetailsService usersDetailsService;
    private final RoleRepository roleRepository;

    @Autowired
    public UsersController(UsersDetailsService usersDetailsService, RoleRepository roleRepository) {
        this.usersDetailsService = usersDetailsService;
        this.roleRepository = roleRepository;
    }


    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/admin")
    public String getUsers(ModelMap model) {
        model.addAttribute("users", usersDetailsService.findAll());
        return "users";
    }

    @GetMapping("/user")
    public String userInfo(ModelMap model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getAuthorities());
        model.addAttribute("user", usersDetailsService.show(user.getId()));
        return "user";
    }

    @GetMapping("/admin/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", usersDetailsService.show(id));
        return "show";
    }

    @GetMapping("/admin/show")
    public String show_by_id(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", usersDetailsService.show(id));
        return "show";
    }
    @GetMapping("/admin/new")
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "new";
    }

    @PostMapping()
    public String add(@ModelAttribute("user") User user) {
        usersDetailsService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", usersDetailsService.show(id));
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        usersDetailsService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        usersDetailsService.delete(id);
        return "redirect:/admin";
    }

}
