package com.voronkov.cafevoiter.controller.user;

import com.voronkov.cafevoiter.model.User;
import com.voronkov.cafevoiter.service.UserService;
import com.voronkov.cafevoiter.utils.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.voronkov.cafevoiter.TestUtil.readFromJson;
import static com.voronkov.cafevoiter.TestUtil.userHttpBasic;
import static com.voronkov.cafevoiter.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserRestControllerTest {

    private static Logger log = LoggerFactory.getLogger(UserRestControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private static final String REST_URL = "/users/profile";

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(userService.findById(USER_ID))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(userService.findById(USER_ID)));
    }

    @Test
    void register() throws Exception {
        User created = new User(null, "newemail@ya.ru", "newPassword");

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isCreated());
        User returned = readFromJson(action, User.class);

        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(userService.findByEmail("newemail@ya.ru"), created);
    }

    @Test
    void update() throws Exception {
        User updated = new User(null, "newemail@ya.ru", "newPassword");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(userService.findById(USER_ID)))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());;

        assertMatch(userService.findByEmail("newemail@ya.ru"), new User(userService.findById(USER_ID)));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(userService.findById(USER_ID))))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), List.of(ADMIN, USER2));
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateInvalid() throws Exception {
        User updated = new User(null, null, "password");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(userService.findById(USER_ID)))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}