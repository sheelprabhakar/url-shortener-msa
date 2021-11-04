package com.code4copy.shortnerservice.rest.controller;

import com.code4copy.shortnerservice.core.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/V1/")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;
    @Autowired
    public ShortUrlController(final ShortUrlService shortUrlService){
        this.shortUrlService = shortUrlService;
    }
}
