package com.lapanik.kameleoontrialtask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lapanik.kameleoontrialtask.model.dto.QuoteDto;
import com.lapanik.kameleoontrialtask.model.dto.UserDto;
import com.lapanik.kameleoontrialtask.model.dto.VoteDto;
import com.lapanik.kameleoontrialtask.model.entity.Quote;
import com.lapanik.kameleoontrialtask.service.QuoteService;
import com.lapanik.kameleoontrialtask.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ApiTest extends KameleoonTrialTaskApplicationTests {
    private static final String USERNAME = "username";

    @Autowired
    private UserService userService;

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private Quote quote;

    @BeforeEach
    public void init() {
        UserDto userDto = userService.createUserService(new UserDto(USERNAME, "password", "username@username.com"));
        quote = quoteService.createQuote(new QuoteDto("content", userDto.getUsername()));
    }

    @Test
    public void whenCheckNotExistUsername_thenFalseInResponse() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/user/check/username")
                        .param(USERNAME, "notExist")
        );

        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$", CoreMatchers.is(false)));
    }

    @Test
    public void whenGetGraph_thenMapInResponse() throws Exception {
        mockMvc.perform(
                post("/vote/like")
                        .content(objectMapper.writeValueAsString(new VoteDto(quote.getId(), USERNAME)))
                        .contentType(APPLICATION_JSON)
        );
        mockMvc.perform(get("/quote/graph").param("quoteId", quote.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(24)));

    }


}
