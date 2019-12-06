package com.voronkov.cafevoiter.controller;

import com.voronkov.cafevoiter.model.User;
import com.voronkov.cafevoiter.service.UserService;
import com.voronkov.cafevoiter.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserRestController {

    private static Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final UserService userService;

    private final UserValidator userValidator;

    @Autowired
    public UserRestController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    public User getOne(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @PostMapping
    public User create(@RequestBody User user, BindingResult result) {
        userValidator.validate(user, result);
        if (!result.hasErrors()) {
            return userService.save(user);
        }
        return null;
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id") User userFromDb, @RequestBody User user) {
        BeanUtils.copyProperties(user, userFromDb, "id", "enabled", "roles");
        return userService.update(userFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") User user) {
        userService.delete(user);
    }
}
