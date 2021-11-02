package com.code4copy.tokenservice.core.service.impl;

import com.code4copy.tokenservice.core.dao.TokenLogRepository;
import com.code4copy.tokenservice.core.domain.TokenLogDO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TokenServiceImplTest {
    @InjectMocks
    TokenServiceImpl tokenService;
    @Mock
    TokenLogRepository tokenLogRepository;
}
