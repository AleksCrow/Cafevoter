//package com.voronkov.cafevoiter.controller;
//
//import com.voronkov.cafevoiter.model.User;
//import com.voronkov.cafevoiter.service.UserService;
//import com.voronkov.cafevoiter.validator.UserValidator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserValidator userValidator;
//
//    @GetMapping("/registration")
//    public String registration(Model model) {
//        model.addAttribute("userForm", new User());
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String registration(@ModelAttribute("userForm") User userForm, BindingResult result, Model model) {
//        userValidator.validate(userForm, result);
//        if (result.hasErrors()) {
//            return "registration";
//        }
//        userService.save(userForm);
////        Security
//        return "redirect: /allUsers";
//    }
//
////    login
//
//    @GetMapping("/users")
//    public String getAllUsers(Model model) {
//        model.addAttribute("users", userService.getAll());
//        return "users";
//    }
//
////    Admin Panel
//}
