package com.code4copy.tokenservice.core.service.impl;

import com.code4copy.tokenservice.core.dao.TokenLogRepository;
import com.code4copy.tokenservice.core.domain.TokenLogDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TokenServiceImplTest {
    @InjectMocks
    TokenServiceImpl tokenService;
    @Mock
    TokenLogRepository tokenLogRepository;

    @BeforeEach
    void setUp() {
        TokenLogDO tokenLogDO =  new TokenLogDO();
        tokenLogDO.setRange(1, 10);
        tokenLogDO.setCreatedBy("");
        tokenLogDO.setCreateAt(Calendar.getInstance());
        tokenLogDO.setId(1);
        Mockito.when(tokenLogRepository.maxToNumber()).thenReturn(0L);
        Mockito.when(tokenLogRepository.save(ArgumentMatchers.any()))
                .thenReturn(tokenLogDO);

    }
    @Test
    public void test_getNextTokenRange_ok(){
        TokenLogDO nextTokenRange = this.tokenService.getNextTokenRange();
        assertEquals(nextTokenRange.getFromNumber(),1);
        assertEquals(nextTokenRange.getToNumber(),10);
    }
}
