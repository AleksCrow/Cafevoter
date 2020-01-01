package com.voronkov.cafevoiter.service;

import com.voronkov.cafevoiter.model.Role;
import com.voronkov.cafevoiter.model.User;
import com.voronkov.cafevoiter.repository.CrudUserRepository;
import com.voronkov.cafevoiter.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    public List<User> getAll() {
        return userRepository.findAll();

    }

    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public User save(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }
}
