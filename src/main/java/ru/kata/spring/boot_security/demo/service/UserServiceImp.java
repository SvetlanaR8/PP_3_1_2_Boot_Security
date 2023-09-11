package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserDao;


import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImp implements UserService {


    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImp(UserDao userDao, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(setRoles(user));
    }

    @Override
    public User show(Long id) {
        return userDao.show(id);
    }

    @Transactional
    @Override
    public void update(Long id, User user) {
        User userToBeUpdated = userDao.show(id);
        userToBeUpdated.setFirstName(user.getFirstName());
        userToBeUpdated.setLastName(user.getLastName());
        userToBeUpdated.setRoles(user.getRoles());
        if (!userToBeUpdated.getPassword().equals(user.getPassword())) {
            userToBeUpdated.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.add(setRoles(userToBeUpdated));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }


    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    private User setRoles(User user) {
        List<Role> userRoles = user.getRoles();
        List<Role> allRoles = roleService.findAll();
        List<Role> correctUserRoles = new ArrayList<Role>();
        for (Role role : allRoles) {
            for (Role userRole : userRoles) {
                if (role.getName().equals(userRole.getName())) {
                    correctUserRoles.add(role);
                }
            }
        }
        user.setRoles(correctUserRoles);
        return user;
    }
}
