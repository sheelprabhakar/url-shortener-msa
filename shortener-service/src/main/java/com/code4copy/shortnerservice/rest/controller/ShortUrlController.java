package com.code4copy.shortnerservice.rest.controller;

import com.code4copy.shortnerservice.adapter.RestAdapterV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shortener/api/v1/")
public class ShortUrlController {

    private final RestAdapterV1 restAdapterV1;
    @Autowired
    public ShortUrlController(final RestAdapterV1 restAdapterV1){
        this.restAdapterV1 = restAdapterV1;
    }

    @GetMapping(produces = "application/text")
    public ResponseEntity<String> getShortUrl(@RequestParam("url") String url){
        if(!StringUtils.hasLength(url)){
            return ResponseEntity.badRequest().body("Not valid url string");
        }
        String shortUrl = this.restAdapterV1.getShortUrl(url);
        return ResponseEntity.ok().body(shortUrl);
    }
}
