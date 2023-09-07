package ru.kata.spring.boot_security.demo.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;


@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }
    @Override
    public User show(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.detach(user);
        return user;
    }

    @Override
    public void update(Long id, User user) {
        User userToBeUpdated = entityManager.find(User.class, id);
        userToBeUpdated.setFirstName(user.getFirstName());
        userToBeUpdated.setLastName(user.getLastName());
        userToBeUpdated.setPassword(user.getPassword());
        userToBeUpdated.setRoles(user.getRoles());
        entityManager.merge(userToBeUpdated);
    }

    @Override
    public void delete(Long id) {
        User userToBeDeleted = entityManager.find(User.class, id);
        entityManager.remove(userToBeDeleted);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        Query query = entityManager.createQuery("FROM User");
        return query.getResultList();
    }
    @Override
    //  @Query("Select u from User u left join fetch u.roles where u.username=:username")
     public User findByUsername(String username) {
        Query query = entityManager.createQuery("SELECT u FROM User u LEFT JOIN u.roles WHERE u.username=:username");
        query.setParameter("username",username);

        return (User) query.getSingleResult();
    }
}
