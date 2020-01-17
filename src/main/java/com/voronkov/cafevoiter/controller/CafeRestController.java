package com.voronkov.cafevoiter.controller;

import com.voronkov.cafevoiter.model.Cafe;
import com.voronkov.cafevoiter.model.Meal;
import com.voronkov.cafevoiter.model.User;
import com.voronkov.cafevoiter.service.CafeService;
import com.voronkov.cafevoiter.to.CafeTo;
import com.voronkov.cafevoiter.utils.CafeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.voronkov.cafevoiter.utils.CafeUtil.createWithVote;
import static com.voronkov.cafevoiter.utils.DateTimeUtil.adjustEndDateTime;
import static com.voronkov.cafevoiter.utils.DateTimeUtil.adjustStartDateTime;

@RestController
@RequestMapping("cafes")
public class CafeRestController {

    private static Logger log = LoggerFactory.getLogger(CafeRestController.class);

    private final CafeService cafeService;

    @Autowired
    public CafeRestController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @GetMapping
    public List<CafeTo> getAll() {
        log.info("LOG список кафе получен");
        return CafeUtil.getCafesWithVotes(cafeService.getAll());
    }

    @GetMapping("{id}")
    public CafeTo get(@PathVariable("id") int id) {
        log.info("LOG кафе с id: {} найдено", id);
        return createWithVote(cafeService.getById(id));
    }

    @GetMapping("meals/{id}")
    public List<Meal> getMeals(@PathVariable("id") int id) {
        log.info("LOG меню кафе с id: {} найдено", id);
        return cafeService.getMeals(id);
    }

    @GetMapping("vote/{id}")
    public List<CafeTo> vote(@AuthenticationPrincipal User currentUser, @PathVariable("id") int cafeId) {
        cafeService.vote(currentUser, cafeId);
        log.info("LOG голос отправлен");
        return CafeUtil.getCafesWithVotes(cafeService.getAll());
    }

    @GetMapping("/filter")
    public List<CafeTo> getBetween(@RequestParam(name = "startDate", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   LocalDate start,
                                   @RequestParam(name = "endDate", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   LocalDate end) {
        List<Cafe> cafeDateFiltered = cafeService.getByDateOrBetweenDateTimes(adjustStartDateTime(start), adjustEndDateTime(end));
        return CafeUtil.getCafesWithVotes(cafeDateFiltered);
    }
}


//3. Сделать тесты
//доработать создание юзера
//добавить кеш
//6. добавить шифровавание
//создать описание

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
