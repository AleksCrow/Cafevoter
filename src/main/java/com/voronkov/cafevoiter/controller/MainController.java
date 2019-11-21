package com.voronkov.cafevoiter.controller;

import com.voronkov.cafevoiter.model.User;
import com.voronkov.cafevoiter.repository.CafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private final CafeRepository cafeRepository;

    @Autowired
    public MainController(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {

        HashMap<Object, Object> data = new HashMap<>();

        data.put("profile", user);
        data.put("cafes", cafeRepository.findAll());

        model.addAttribute("frontendData", data);

        return "index";
    }
}
