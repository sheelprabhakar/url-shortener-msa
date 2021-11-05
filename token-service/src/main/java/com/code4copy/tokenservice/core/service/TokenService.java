package com.code4copy.tokenservice.core.service;

import com.code4copy.tokenservice.core.domain.TokenLogDO;

public interface TokenService {
    TokenLogDO getNextTokenRange();
}
