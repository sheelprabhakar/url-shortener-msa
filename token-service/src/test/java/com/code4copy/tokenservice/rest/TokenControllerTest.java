package com.code4copy.tokenservice.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
public class TokenControllerTest extends AbstractIntegrationTest{
    private final String BASE_URL="/api/v1/";
    @Test
    public void test_get_token_ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL+"next/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fromNumber").isNumber());
    }
}
