package com.code4copy.shortnerservice.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShortUrlControllerTest extends  AbstractIntegrationTest{

    private final String BASE_URL="/api/v1/";

    @Test
    void givenCassandraContainer_whenSpringContextIsBootstrapped_thenContainerIsRunningWithNoExceptions() {
        assertThat(cassandra.isRunning()).isTrue();
    }

    @Test
    public void test_get_short_url_ok() throws Exception {
        String shortUrl = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL)
                        .param("url", "https://www.bing.com/search?q=spring+boot+cassandra+container+integration+test&cvid=3eca5ae0004c467d939e5edf5665cae6&aqs=edge..69i57.24687j0j1&pglt=171&PC=EDGEDSE&first=6&FORM=PERE")
                        .accept("text/html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(shortUrl, "http://localhost/1000000");

        shortUrl = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL)
                        .param("url", "https://www.bing.com/search?q=spring+boot+cassandra+container+integration+test&cvid=3eca5ae0004c467d939e5edf5665cae6&aqs=edge..69i57.24687j0j1&pglt=171&PC=EDGEDSE&first=6&FORM=PERE")
                        .accept("text/html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(shortUrl, "http://localhost/2000000");
    }
}
