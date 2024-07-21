package com.code4copy.tokenservice.core.service.api;

import com.code4copy.tokenservice.core.entity.TokenLogEntity;

public interface TokenService {
    TokenLogEntity getNextTokenRange();
}
