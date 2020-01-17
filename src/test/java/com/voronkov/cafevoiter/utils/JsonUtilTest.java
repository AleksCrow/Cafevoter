package com.voronkov.cafevoiter.utils;

import com.voronkov.cafevoiter.model.Cafe;
import com.voronkov.cafevoiter.utils.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.voronkov.cafevoiter.CafeTestData.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JsonUtilTest {

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(CAFES_TO_TESTS);
        System.out.println(json);
        List<Cafe> cafes = JsonUtil.readValues(json, Cafe.class);
        assertMatch(cafes, CAFES_TO_TESTS);
    }

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(CAFE_TO_TEST1);
        System.out.println(json);
        Cafe cafe = JsonUtil.readValue(json, Cafe.class);
        assertMatch(cafe, CAFE_TO_TEST1);
    }
}