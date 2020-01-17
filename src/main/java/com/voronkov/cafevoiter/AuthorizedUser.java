package com.voronkov.cafevoiter;

import com.voronkov.cafevoiter.model.Role;
import com.voronkov.cafevoiter.model.User;

import java.util.Set;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private int id;
    private Set<Role> roles;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.id = user.getId();
    }

    public int getId() {
        return id;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
