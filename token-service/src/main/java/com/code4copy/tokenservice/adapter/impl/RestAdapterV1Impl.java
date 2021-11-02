package com.code4copy.tokenservice.adapter.impl;

import com.code4copy.tokenservice.adapter.RestAdapterV1;
import com.code4copy.tokenservice.core.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestAdapterV1Impl implements RestAdapterV1 {
    private final TokenService urlTokenService;

    @Autowired
    public  RestAdapterV1Impl(final TokenService urlTokenService){
        this.urlTokenService = urlTokenService;
    }
}
