package com.voronkov.cafevoiter.controller.cafe;

import com.voronkov.cafevoiter.service.CafeService;
import com.voronkov.cafevoiter.to.CafeTo;
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

import static com.voronkov.cafevoiter.CafeTestData.*;
import static com.voronkov.cafevoiter.TestUtil.readFromJsonMvcResult;
import static com.voronkov.cafevoiter.utils.CafeUtil.createWithVote;
import static com.voronkov.cafevoiter.utils.CafeUtil.getCafesWithVotes;
import static com.voronkov.cafevoiter.utils.TimeUtil.limitHourForVote;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CafeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CafeService cafeService;

    private static final String ADMIN_URL = "/admin/";

    @Test
    @WithMockUser(roles = {"USER"})
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(getCafesWithVotes(cafeService.getAll())));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes/" + CAFE1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, CafeTo.class),
                        createWithVote(cafeService.getById(CAFE1_ID))));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void getMeals() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes/meals/" + CAFE1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(cafeService.getMeals(CAFE1_ID)));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void vote() throws Exception{
        limitHourForVote = 24;
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes/vote/" + CAFE_FOR_VOTE))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(getCafesWithVotes(cafeService.getAll())));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void notCanVote() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes/vote/" + CAFE1_ID))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void filter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes/filter")
                .param("startDate", "2020-01-01")
                .param("endDate", "2020-01-01"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(createWithVote(cafeService.getById(CAFE_FILTERED))));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void filterAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes/filter?startDate=&endTime="))
                .andExpect(status().isOk())
                .andExpect(contentJson(getCafesWithVotes(cafeService.getAll())));
    }

    @Test
    void getUnauth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes/" + CAFE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cafes/999"))
                .andExpect(status().isNotFound());
    }
}