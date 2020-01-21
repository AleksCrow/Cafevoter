package com.voronkov.restaurantvoter;

import com.voronkov.restaurantvoter.model.Role;
import com.voronkov.restaurantvoter.model.User;
import com.voronkov.restaurantvoter.utils.json.JsonUtil;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.Set;

import static com.voronkov.restaurantvoter.TestUtil.readFromJsonMvcResult;
import static com.voronkov.restaurantvoter.TestUtil.readListFromJsonMvcResult;
import static com.voronkov.restaurantvoter.model.User.USER_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {

    public static final int ADMIN_ID = USER_SEQ;
    public static final int USER_ID = USER_SEQ + 1;
    public static final int USER2_ID = USER_SEQ + 2;

    public static final User ADMIN = new User(ADMIN_ID, "admin@gmail.com", "admin", Role.ROLE_USER, Role.ROLE_ADMIN);
    public static final User USER2 = new User(USER2_ID, "user@yandex.ru", "123", Role.ROLE_USER);
    public static final Set<User> VOTES = Set.of(ADMIN);

    public static final User USER = new User(USER_ID, "newEmail@mail.ru", "pass", Role.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }

    public static ResultMatcher contentJson(Iterable<User> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, User.class)).isEqualTo(expected);
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
