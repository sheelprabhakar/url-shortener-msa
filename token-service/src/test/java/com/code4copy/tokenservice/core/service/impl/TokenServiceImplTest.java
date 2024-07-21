package com.code4copy.tokenservice.core.service.impl;

import com.code4copy.tokenservice.core.entity.TokenLogEntity;
import com.code4copy.tokenservice.core.repository.TokenLogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {
  @InjectMocks
  TokenServiceImpl tokenService;
  @Mock
  TokenLogRepository tokenLogRepository;

  @BeforeEach
  void setUp() {
    Mockito.when(tokenLogRepository.maxToNumber()).thenReturn(0L);
    Mockito.when(tokenLogRepository.save(ArgumentMatchers.any()))
        .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
  }

  @Test
  void test_getNextTokenRange_ok() {
    TokenLogEntity nextTokenRange = this.tokenService.getNextTokenRange();
    Assertions.assertEquals(1, nextTokenRange.getFromNumber());
    Assertions.assertEquals(1000, nextTokenRange.getToNumber());
  }
}
