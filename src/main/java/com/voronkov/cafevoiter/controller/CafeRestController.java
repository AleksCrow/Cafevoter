package com.voronkov.cafevoiter.controller;

import com.voronkov.cafevoiter.model.Cafe;
import com.voronkov.cafevoiter.repository.CafeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("cafes")
public class CafeRestController {

    private final CafeRepository cafeRepository;

    @Autowired
    public CafeRestController(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Cafe>> getAllCafes() {
        List<Cafe> cafes = cafeRepository.findAll();

        if (cafes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cafes, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public Cafe getOne(@PathVariable("id")Cafe cafe) {
        return cafe;
    }

    @PostMapping
    public Cafe create(@RequestBody Cafe cafe) {
        cafe.setCreatedDate(LocalDateTime.now());
        return cafeRepository.save(cafe);
    }

    @PutMapping("{id}")
    public Cafe update(@PathVariable("id") Cafe cafeFromDb, @RequestBody Cafe cafe) {
        //cafeFromDb - кафе из бд, которе редактируем, берём его значения и заменяем новыми, всеми кроме id и даты
        BeanUtils.copyProperties(cafe, cafeFromDb, "id", "date");
        return cafeRepository.save(cafeFromDb);
    }

    @DeleteMapping("{id}")
    public @ResponseBody void delete(@PathVariable("id") Cafe cafe) {
        cafeRepository.delete(cafe);
    }
}


/*команды для консоли

// GET all
fetch('/message/').then(response => response.json().then(console.log))

// GET one
        fetch('/message/2').then(response => response.json().then(console.log))

// POST add new one
        fetch(
        '/message',
        {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ text: 'Fourth message (4)', id: 10 })
        }
        ).then(result => result.json().then(console.log))

// PUT save existing
        fetch(
        '/message/4',
        {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ text: 'Fourth message', id: 10 })
        }
        ).then(result => result.json().then(console.log));

// DELETE existing
        fetch('/message/4', { method: 'DELETE' }).then(result => console.log(result))*/
