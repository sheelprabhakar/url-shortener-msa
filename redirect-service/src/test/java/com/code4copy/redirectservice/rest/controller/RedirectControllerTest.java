package com.code4copy.redirectservice.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.code4copy.redirectservice.common.Constants;
import com.code4copy.redirectservice.core.entity.ShortUrlMapEntity;
import com.code4copy.redirectservice.core.repository.ShortUrlRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class RedirectControllerTest extends AbstractIntegrationTest {
  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private ShortUrlRepository shortUrlRepository;

  @Test
  void givenCassandraContainer_whenSpringContextIsBootstrapped_thenContainerIsRunningWithNoExceptions() {
    assertThat(cassandra.isRunning()).isTrue();
  }

  @Test
  void test_get_redirect_not_found() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders
            .get("/555555555"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andReturn().getResponse().getContentAsString();
  }

  @Test
  void test_get_redirect_from_cache_ok() throws Exception {
    String key = "key1";
    String value = "https://wwww.google.com";
    this.redisTemplate.opsForHash().put(Constants.CACHE_KEY, key, value);

    this.mockMvc.perform(MockMvcRequestBuilders
            .get("/key1"))
        .andDo(print())
        .andExpect(status().is3xxRedirection());
  }

  @Test
  void test_get_redirect_from_db_ok() throws Exception {
    ShortUrlMapEntity obj = new ShortUrlMapEntity();
    obj.setCreatedOn(LocalDate.now());
    obj.setShortUrl("key2");
    obj.setUrl("https://wwww.google.com");
    this.shortUrlRepository.save(obj);
    this.mockMvc.perform(MockMvcRequestBuilders
            .get("/key2"))
        .andDo(print())
        .andExpect(status().is3xxRedirection());
  }
}
