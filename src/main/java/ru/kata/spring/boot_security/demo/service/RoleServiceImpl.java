package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleDao;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDao roleDao;
    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    @Transactional
    @Override
    public void add(Role role) {
        roleDao.add(role);
    }

    @Transactional
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role show(Long id) {
        return roleDao.show(id);
    }
    @Transactional
    @Override
    public void update(Long id, Role role) {
        roleDao.update(id, role);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        roleDao.delete(id);

    }
}
