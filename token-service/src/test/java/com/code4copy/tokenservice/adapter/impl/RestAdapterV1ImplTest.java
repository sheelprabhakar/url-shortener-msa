package com.code4copy.tokenservice.adapter.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.code4copy.tokenservice.core.entity.TokenLogEntity;
import com.code4copy.tokenservice.core.service.api.TokenService;
import com.code4copy.tokenservice.rest.resource.TokenLogResource;
import java.util.Calendar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestAdapterV1ImplTest {
  @InjectMocks
  RestAdapterV1Impl restAdapterV1;
  @Mock
  TokenService tokenService;

  @Test
  void test_token_log_do_to_res() {
    TokenLogEntity tokenLogEntity = new TokenLogEntity();
    tokenLogEntity.setRange(1, 10);
    tokenLogEntity.setCreatedBy("");
    tokenLogEntity.setCreatedAt(Calendar.getInstance());
    tokenLogEntity.setId(1);
    Mockito.when(tokenService.getNextTokenRange()).thenReturn(tokenLogEntity);

    TokenLogResource tokenLogResource = restAdapterV1.getNextTokenRange();
    assertEquals(tokenLogEntity.getFromNumber(), tokenLogResource.getFromNumber());
  }
}
