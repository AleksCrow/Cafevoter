package com.voronkov.cafevoiter;

import com.voronkov.cafevoiter.model.Cafe;
import com.voronkov.cafevoiter.model.Meal;
import com.voronkov.cafevoiter.to.CafeTo;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.List;

import static com.voronkov.cafevoiter.TestUtil.readListFromJsonMvcResult;
import static com.voronkov.cafevoiter.UserTestData.VOTES;
import static com.voronkov.cafevoiter.model.Cafe.CAFE_START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class CafeTestData {

    public static final int CAFE1_ID = CAFE_START_SEQ + 3;
    public static final int CAFE_FOR_VOTE = CAFE_START_SEQ + 7;
    public static final int CAFE_FILTERED = CAFE_START_SEQ + 5;

    public static final Meal MEAL1 = new Meal("Пирожок", 1.5);
    public static final Meal MEAL2 = new Meal("Чебурек", 3.0);

    public static final List<Meal> MEALS = List.of(MEAL1, MEAL2);

    public static final Cafe CAFE_TO_TEST1 = new Cafe(1, "Cafe 1", LocalDate.of(2019, 12, 12), List.of(new Meal("Burger", 2.5)));
    public static final Cafe CAFE_TO_TEST2 = new Cafe(2, "Cafe 2", LocalDate.of(2019, 12, 12), List.of(new Meal("Cheesburger", 3.0)));
    public static final List<Cafe> CAFES_TO_TESTS = List.of(CAFE_TO_TEST1, CAFE_TO_TEST2);

    public static Cafe getCreated() {
        return new Cafe(null, "Ресторан", LocalDate.of(2019, 11, 10), MEALS);
    }

    public static Cafe getUpdated() {
        return new Cafe(CAFE1_ID, "Столовая - Ромашка", LocalDate.of(2019, 11, 11), MEALS, VOTES);
    }

    public static void assertMatch(Cafe actual, Cafe expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(CafeTo actual, CafeTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Cafe> actual, Iterable<Cafe> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("meals").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(CafeTo... expected) {
        return contentJson(List.of(expected));
    }

    public static ResultMatcher contentJson(Iterable<CafeTo> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, CafeTo.class)).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(List<Meal> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, Meal.class)).isEqualTo(expected);
    }
}
