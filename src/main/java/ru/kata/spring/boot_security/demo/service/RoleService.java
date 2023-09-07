package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {

    void add(Role role);
    List<Role> findAll();

    Role show(Long id);

    void update (Long id, Role role);
    void delete(Long id);
}
