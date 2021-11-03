package com.code4copy.tokenservice.adapter.impl;

import com.code4copy.tokenservice.adapter.RestAdapterV1;
import com.code4copy.tokenservice.core.domain.TokenLogDO;
import com.code4copy.tokenservice.core.service.TokenService;
import com.code4copy.tokenservice.rest.resource.TokenResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestAdapterV1Impl implements RestAdapterV1 {
    private final TokenService urlTokenService;

    @Autowired
    public  RestAdapterV1Impl(final TokenService urlTokenService){
        this.urlTokenService = urlTokenService;
    }

    @Override
    public TokenResource getNextTokenRange() {
        return tokenDoToResource(this.urlTokenService.getNextTokenRange());
    }

    private TokenResource tokenDoToResource(TokenLogDO logDO){
        TokenResource resource = new TokenResource();
        resource.setFromNumber(logDO.getFromNumber());
        resource.setToNumber(logDO.getToNumber());
        return resource;
    }
}
