package com.voronkov.restaurantvoter;

import com.voronkov.restaurantvoter.model.Restaurant;
import com.voronkov.restaurantvoter.model.Meal;
import com.voronkov.restaurantvoter.to.RestaurantTo;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.List;

import static com.voronkov.restaurantvoter.TestUtil.readListFromJsonMvcResult;
import static com.voronkov.restaurantvoter.UserTestData.VOTES;
import static com.voronkov.restaurantvoter.model.Restaurant.RESTAURANT_START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class CafeTestData {

    public static final int RESTAURANT_ID = RESTAURANT_START_SEQ + 3;
    public static final int RESTAURANT_FOR_VOTE = RESTAURANT_START_SEQ + 7;
    public static final int RESTAURANT_FILTERED = RESTAURANT_START_SEQ + 5;

    public static final Meal MEAL1 = new Meal("Пирожок", 1.5);
    public static final Meal MEAL2 = new Meal("Чебурек", 3.0);

    public static final List<Meal> MEALS = List.of(MEAL1, MEAL2);

    public static final Restaurant RESTAURANT_TO_TEST_1 = new Restaurant(1, "Cafe 1", LocalDate.of(2019, 12, 12), List.of(new Meal("Burger", 2.5)));
    public static final Restaurant RESTAURANT_TO_TEST_2 = new Restaurant(2, "Cafe 2", LocalDate.of(2019, 12, 12), List.of(new Meal("Cheesburger", 3.0)));
    public static final List<Restaurant> RESTAURANTS_TO_TESTS = List.of(RESTAURANT_TO_TEST_1, RESTAURANT_TO_TEST_2);

    public static Restaurant getCreated() {
        return new Restaurant(null, "Ресторан", LocalDate.of(2019, 11, 10), MEALS);
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "Столовая - Ромашка", LocalDate.of(2019, 11, 11), MEALS, VOTES);
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(RestaurantTo actual, RestaurantTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("meals").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(RestaurantTo... expected) {
        return contentJson(List.of(expected));
    }

    public static ResultMatcher contentJson(Iterable<RestaurantTo> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, RestaurantTo.class)).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(List<Meal> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, Meal.class)).isEqualTo(expected);
    }
}
