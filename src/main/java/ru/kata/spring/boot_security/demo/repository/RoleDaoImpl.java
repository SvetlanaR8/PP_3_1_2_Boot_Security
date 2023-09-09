package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void add(Role role) {
        if (Objects.isNull(role.getId())) {
            entityManager.persist(role);
        } else {
            if (!Objects.isNull(show(role.getId()))) {
                entityManager.merge(role);
            }
        }
    }

    @Override
    public List<Role> findAll() {
        Query query = entityManager.createQuery("FROM Role");
        return query.getResultList();
    }

    @Override
    public Role show(Long id) {
        Role role = entityManager.find(Role.class, id);
        return role;
    }

    @Override
    public void update(Long id, Role role) {
        Role roleToBeUpdated = entityManager.find(Role.class, id);
        roleToBeUpdated.setName(role.getName());
        entityManager.merge(roleToBeUpdated);

    }

    @Override
    public void delete(Long id) {
        Role roleToBeDeleted = entityManager.find(Role.class, id);
        entityManager.remove(roleToBeDeleted);
    }
}
