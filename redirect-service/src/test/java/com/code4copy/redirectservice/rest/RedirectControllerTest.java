package com.code4copy.redirectservice.rest;

import com.code4copy.redirectservice.common.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RedirectControllerTest extends  AbstractIntegrationTest{
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void givenCassandraContainer_whenSpringContextIsBootstrapped_thenContainerIsRunningWithNoExceptions() {
        assertThat(cassandra.isRunning()).isTrue();
    }

    @Test
    public void test_get_redirect_not_found() throws Exception {
        String shortUrl = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/555555555"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
    }
    @Test
    public void test_get_redirect_from_cache_ok() throws Exception {
        this.redisTemplate.opsForHash().put(Constants.CACHE_KEY, "key1", "https://wwww.google.com");
        System.out.println( this.redisTemplate.opsForHash().get(Constants.CACHE_KEY, "key1"));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/key1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}
