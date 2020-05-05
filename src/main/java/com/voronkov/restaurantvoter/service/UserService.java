package com.voronkov.restaurantvoter.service;

import com.voronkov.restaurantvoter.AuthorizedUser;
import com.voronkov.restaurantvoter.model.Role;
import com.voronkov.restaurantvoter.model.User;
import com.voronkov.restaurantvoter.repository.CrudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final CrudUserRepository userRepository;

    @Autowired
    public UserService(CrudUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable("users")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return find(id);
    }

    @CacheEvict(value = "users", allEntries = true)
    public User save(User user) {
        if (user.getId() == null) {
            Set<Role> roles = new HashSet<>();
            roles.add(Role.ROLE_USER);
            user.setRoles(roles);
        }
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        userRepository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        User user = find(id);
        userRepository.delete(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private User find(int id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
