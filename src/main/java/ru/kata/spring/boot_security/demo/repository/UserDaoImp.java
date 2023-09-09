package ru.kata.spring.boot_security.demo.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;
import java.util.Objects;


@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {

        if (Objects.isNull(user.getId())) {
            entityManager.persist(user);
        } else {
            if (!Objects.isNull(show(user.getId()))) {
                entityManager.merge(user);
            }
        }
    }

    @Override
    public User show(Long id) {
        return entityManager.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void delete(Long id) {
        entityManager.createQuery("DELETE FROM User WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        Query query = entityManager.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles", User.class);
        return query.getResultList();
    }
    @Override
     public User findByUsername(String username) {
        Query query = entityManager.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username=:username", User.class);
        query.setParameter("username",username);
        return (User) query.getSingleResult();
    }
}
