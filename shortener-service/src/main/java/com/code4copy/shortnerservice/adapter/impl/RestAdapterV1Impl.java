package com.code4copy.shortnerservice.adapter.impl;

import com.code4copy.shortnerservice.adapter.RestAdapterV1;
import com.code4copy.shortnerservice.core.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestAdapterV1Impl implements RestAdapterV1 {
    private final ShortUrlService shortUrlService;
    @Autowired
    public  RestAdapterV1Impl(final ShortUrlService shortUrlService){
        this.shortUrlService = shortUrlService;
    }

    @Override
    public String getShortUrl(String url) {
        return this.shortUrlService.generateShortUrl(url);
    }
}
