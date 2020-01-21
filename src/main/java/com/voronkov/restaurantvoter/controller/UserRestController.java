package com.voronkov.restaurantvoter.controller;

import com.voronkov.restaurantvoter.AuthorizedUser;
import com.voronkov.restaurantvoter.model.Role;
import com.voronkov.restaurantvoter.model.User;
import com.voronkov.restaurantvoter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users/profile")
public class UserRestController {

    private static Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User getOne(@AuthenticationPrincipal AuthorizedUser currentUser) {
        log.info("LOG юзер с id найден: {}", currentUser.getId());
        return userService.findById(currentUser.getId());
    }

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@RequestBody User user) {
        User created = userService.save(user);
        log.info("LOG новый юзер: {} создан", user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthorizedUser currentUser, @Valid @RequestBody User user) {
        user.setId(currentUser.getId());
        user.setRoles(currentUser.getRoles());
        log.info("LOG юзер c id: {} обновлен", currentUser.getId());
        userService.update(user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser currentUser) {
        log.info("LOG юзер c id: {} удалён", currentUser.getId());
        userService.delete(currentUser.getId());
    }
}
