package com.voronkov.cafevoiter.controller;

import com.voronkov.cafevoiter.model.User;
import com.voronkov.cafevoiter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserRestController {

    private static Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAll() {
        log.info("LOG Получен список пользователей");
        return userService.getAll();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getOne(@PathVariable("id") int id) {
        log.info("LOG Полльзователь с id: {} найден", id);
        return userService.findById(id);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = userService.save(user);
        log.info("LOG новый юзер: {}", user);
        log.info("LOG новый юзер c id: {} создан", user.getId());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("users/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id") User userFromDb, @RequestBody User user) {
        BeanUtils.copyProperties(user, userFromDb, "id", "enabled", "roles");
        log.info("LOG юзер c id: {} обновлен", user.getId());
        return userService.update(userFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") User user) {
        log.info("LOG юзер c id: {} удалён", user.getId());
        userService.delete(user);
    }
}
