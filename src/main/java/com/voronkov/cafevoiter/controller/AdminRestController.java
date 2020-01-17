package com.voronkov.cafevoiter.controller;

import com.voronkov.cafevoiter.model.Cafe;
import com.voronkov.cafevoiter.model.User;
import com.voronkov.cafevoiter.service.CafeService;
import com.voronkov.cafevoiter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private static Logger log = LoggerFactory.getLogger(AdminRestController.class);

    private final CafeService cafeService;
    private final UserService userService;

    @Autowired
    public AdminRestController(CafeService cafeService, UserService userService) {
        this.cafeService = cafeService;
        this.userService = userService;
    }

    @PostMapping("/cafes")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Cafe> createCafe(@Valid @RequestBody Cafe cafe, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (cafe.getCreatedDate() == null) {
            cafe.setCreatedDate(LocalDate.now());
        }
        Cafe created = cafeService.save(cafe);
        log.info("LOG новое кафе c id: {} создано", cafe.getId());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/admin/cafes/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/cafes/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateCafe(@PathVariable("id") Cafe cafeFromDb, @Valid @RequestBody Cafe cafe) {
        //cafeFromDb - кафе из бд, которе редактируем, берём его значения и заменяем новыми, всеми кроме id и даты
        BeanUtils.copyProperties(cafe, cafeFromDb, "id", "date", "votes");
        log.info("LOG кафе с id: {} обновлено", cafeFromDb.getId());
        cafeService.update(cafeFromDb);
    }

    @DeleteMapping("/cafes/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCafe(@PathVariable("id") int id) {
        log.info("LOG кафе с id: {} удалено", id);
        cafeService.delete(id);
    }

    //FOR USERS

    @GetMapping("/users")
    public List<User> getAllUsers() {
        log.info("LOG Получен список пользователей");
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    public User getOneUser(@PathVariable int id) {
        log.info("LOG Пользователь с id: {} найден", id);
        return userService.findById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        User created = userService.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/admin/users/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.delete(id);
    }

    @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable("id") User userFromDb, @RequestBody User user) {
        BeanUtils.copyProperties(user, userFromDb, "id");
        log.info("LOG данные пользователя с id: {} обновлены", userFromDb.getId());
        userService.update(userFromDb);
    }

    @GetMapping("/users/by")
    public User getByMail(@RequestParam String email) {
        log.info("getByEmail {}", email);
        return userService.findByEmail(email);
    }
}
