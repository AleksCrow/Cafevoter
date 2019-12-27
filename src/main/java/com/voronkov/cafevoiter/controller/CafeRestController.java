package com.voronkov.cafevoiter.controller;

import com.voronkov.cafevoiter.model.Cafe;
import com.voronkov.cafevoiter.model.Meal;
import com.voronkov.cafevoiter.model.User;
import com.voronkov.cafevoiter.service.CafeService;
import com.voronkov.cafevoiter.to.CafeTo;
import com.voronkov.cafevoiter.utils.CafeUtil;
import com.voronkov.cafevoiter.validator.CafeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.voronkov.cafevoiter.utils.DateTimeUtil.adjustEndDateTime;
import static com.voronkov.cafevoiter.utils.DateTimeUtil.adjustStartDateTime;

@RestController
@RequestMapping("cafes")
public class CafeRestController {

    private static Logger log = LoggerFactory.getLogger(CafeRestController.class);

    private final CafeService cafeService;
    private final CafeValidator cafeValidator;

    @Autowired
    public CafeRestController(CafeValidator cafeValidator, CafeService cafeService) {
        this.cafeValidator = cafeValidator;
        this.cafeService = cafeService;
    }

    @GetMapping
    public List<CafeTo> getAllCafes() {
        return CafeUtil.getCafesWithVotes(cafeService.getAll());
    }

    @GetMapping("{id}")
    public CafeTo getOne(@PathVariable("id") int id) {
        return CafeUtil.createWithVote(cafeService.getById(id));
    }

    @GetMapping("{id}/meals")
    public List<Meal> getMeals(@PathVariable("id") int id) {
        return cafeService.getById(id).getMeals();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Cafe create(@RequestBody Cafe cafe, BindingResult result) {
        cafeValidator.validate(cafe, result);
        if (!result.hasErrors()) {
            cafe.setCreatedDate(LocalDate.now());
            return cafeService.save(cafe);
        }
        return null;
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Cafe update(@PathVariable("id") Cafe cafeFromDb, @RequestBody Cafe cafe) {
        //cafeFromDb - кафе из бд, которе редактируем, берём его значения и заменяем новыми, всеми кроме id и даты
        BeanUtils.copyProperties(cafe, cafeFromDb, "id", "date");
        return cafeService.save(cafeFromDb);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable("id") Cafe cafe) {
        cafeService.delete(cafe);
    }

    @GetMapping("{id}/vote")
    public List<CafeTo> vote(@AuthenticationPrincipal User currentUser, @PathVariable("id") Cafe cafe) {
        return CafeUtil.getCafesWithVotes(cafeService.vote(currentUser, cafe));
    }

    @GetMapping("/filter")
    public List<CafeTo> getBetween(@RequestParam(name = "start", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   LocalDate start,
                                   @RequestParam(name = "end", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   LocalDate end) {
        log.info("IN start = {}", start);
        List<Cafe> cafeDateFiltered = cafeService.getByDateOrBetweenDateTimes(adjustStartDateTime(start), adjustEndDateTime(end));
        return CafeUtil.getCafesWithVotes(cafeDateFiltered);
    }
}


//Обработать метод vote, чтобы возвращал список, а не проголосованное кафе
//3. Сделать тесты
//6. добавить шифрование пароля

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
