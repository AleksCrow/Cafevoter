package com.voronkov.restaurantvoter.controller;

import com.voronkov.restaurantvoter.model.Meal;
import com.voronkov.restaurantvoter.model.Restaurant;
import com.voronkov.restaurantvoter.model.User;
import com.voronkov.restaurantvoter.service.RestaurantService;
import com.voronkov.restaurantvoter.to.RestaurantTo;
import com.voronkov.restaurantvoter.utils.RestaurantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.voronkov.restaurantvoter.utils.DateTimeUtil.adjustEndDateTime;
import static com.voronkov.restaurantvoter.utils.DateTimeUtil.adjustStartDateTime;
import static com.voronkov.restaurantvoter.utils.RestaurantUtil.createWithVote;

@RestController
@RequestMapping("restaurants")
public class RestaurantRestController {

    private static Logger log = LoggerFactory.getLogger(RestaurantRestController.class);

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("LOG список кафе получен");
        return RestaurantUtil.getRestaurantWithVotes(restaurantService.getAll());
    }

    @GetMapping("{id}")
    public RestaurantTo get(@PathVariable("id") int id) {
        log.info("LOG кафе с id: {} найдено", id);
        return createWithVote(restaurantService.getById(id));
    }

    @GetMapping("meals/{id}")
    public List<Meal> getMeals(@PathVariable("id") int id) {
        log.info("LOG меню кафе с id: {} найдено", id);
        return restaurantService.getMeals(id);
    }

    @GetMapping("vote/{id}")
    public List<RestaurantTo> vote(@AuthenticationPrincipal User currentUser, @PathVariable("id") int restaurantId) {
        restaurantService.vote(currentUser, restaurantId);
        log.info("LOG голос отправлен");
        return RestaurantUtil.getRestaurantWithVotes(restaurantService.getAll());
    }

    @GetMapping("/filter")
    public List<RestaurantTo> getBetween(@RequestParam(name = "startDate", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   LocalDate start,
                                         @RequestParam(name = "endDate", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   LocalDate end) {
        List<Restaurant> restaurantDateFiltered = restaurantService.getByDateOrBetweenDateTimes(adjustStartDateTime(start), adjustEndDateTime(end));
        return RestaurantUtil.getRestaurantWithVotes(restaurantDateFiltered);
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
