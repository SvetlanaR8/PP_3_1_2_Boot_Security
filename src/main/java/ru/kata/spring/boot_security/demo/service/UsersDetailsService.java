package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UsersDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User show(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.orElse(null);
    }
    @Transactional
    public void add(User user){
        userRepository.save(user);
    }

    @Transactional
    public void update(Long id, User user){
        Optional<User> userToBeUpdated = userRepository.findById(id);
        if (userToBeUpdated.isPresent()) {
            userToBeUpdated.get().setFirstName(user.getFirstName());
            userToBeUpdated.get().setLastName(user.getLastName());
            userToBeUpdated.get().setPassword(user.getPassword());
            userToBeUpdated.get().setRoles(user.getRoles());
            userRepository.save(userToBeUpdated.get());
        }
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }
}
