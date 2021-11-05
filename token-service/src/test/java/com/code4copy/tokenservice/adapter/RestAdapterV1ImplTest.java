package com.code4copy.tokenservice.adapter;

import com.code4copy.tokenservice.adapter.impl.RestAdapterV1Impl;
import com.code4copy.tokenservice.core.domain.TokenLogDO;
import com.code4copy.tokenservice.core.service.TokenService;
import com.code4copy.tokenservice.rest.resource.TokenResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RestAdapterV1ImplTest {
    @InjectMocks
    RestAdapterV1Impl restAdapterV1;
    @Mock
    TokenService tokenService;

    @Test
    public void test_token_log_do_to_res(){
        TokenLogDO tokenLogDO =  new TokenLogDO();
        tokenLogDO.setRange(1, 10);
        tokenLogDO.setCreatedBy("");
        tokenLogDO.setCreateAt(Calendar.getInstance());
        tokenLogDO.setId(1);
        Mockito.when(tokenService.getNextTokenRange()).thenReturn(tokenLogDO);

        TokenResource tokenResource = restAdapterV1.getNextTokenRange();
        assertEquals(tokenLogDO.getFromNumber(), tokenResource.getFromNumber());
    }
}
