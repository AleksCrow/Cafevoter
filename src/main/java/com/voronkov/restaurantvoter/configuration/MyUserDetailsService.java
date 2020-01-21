package com.voronkov.restaurantvoter.configuration;

import com.voronkov.restaurantvoter.AuthorizedUser;
import com.voronkov.restaurantvoter.model.User;
import com.voronkov.restaurantvoter.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    final UserService userService;

    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =  userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new AuthorizedUser(user);
    }
}
