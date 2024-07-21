package com.code4copy.redirectservice.adapter.impl;

import com.code4copy.redirectservice.adapter.api.RestAdapterV1;
import com.code4copy.redirectservice.core.service.api.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestAdapterV1Impl implements RestAdapterV1 {

    private final ShortUrlService shortUrlService;
    @Autowired
    public RestAdapterV1Impl(final ShortUrlService shortUrlService){
        this.shortUrlService = shortUrlService;
    }
    @Override
    public String getLongUrl(final String shortUrl) {
        return this.shortUrlService.getLongUrl(shortUrl);
    }
}
