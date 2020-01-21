package com.voronkov.restaurantvoter.controller.cafe;

import com.voronkov.restaurantvoter.service.RestaurantService;
import com.voronkov.restaurantvoter.to.RestaurantTo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.voronkov.restaurantvoter.CafeTestData.*;
import static com.voronkov.restaurantvoter.TestUtil.readFromJsonMvcResult;
import static com.voronkov.restaurantvoter.utils.RestaurantUtil.*;
import static com.voronkov.restaurantvoter.utils.TimeUtil.limitHourForVote;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RestaurantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantService restaurantService;

    @Test
    @WithMockUser(roles = {"USER"})
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(getRestaurantWithVotes(restaurantService.getAll())));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/" + RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, RestaurantTo.class),
                        createWithVote(restaurantService.getById(RESTAURANT_ID))));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void getMeals() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/meals/" + RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(restaurantService.getMeals(RESTAURANT_ID)));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void vote() throws Exception{
        limitHourForVote = 24;
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/vote/" + RESTAURANT_FOR_VOTE))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(getRestaurantWithVotes(restaurantService.getAll())));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void notCanVote() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/vote/" + RESTAURANT_ID))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void filter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/filter")
                .param("startDate", "2020-01-01")
                .param("endDate", "2020-01-01"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(createWithVote(restaurantService.getById(RESTAURANT_FILTERED))));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void filterAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/filter?startDate=&endTime="))
                .andExpect(status().isOk())
                .andExpect(contentJson(getRestaurantWithVotes(restaurantService.getAll())));
    }

    @Test
    void getUnauth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/" + RESTAURANT_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/999"))
                .andExpect(status().isNotFound());
    }
}