package com.code4copy.redirectservice.rest;

import com.code4copy.redirectservice.adapter.RestAdapterV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;

@Controller
public class RedirectController {
    private final RestAdapterV1 restAdapterV1;

    @Autowired
    public RedirectController(final RestAdapterV1 restAdapterV1){
        this.restAdapterV1 = restAdapterV1;
    }
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> getLongUrl(@PathVariable("shortUrl") String shortUrl){
        String longUrl = this.restAdapterV1.getLongUrl(shortUrl);
        if(longUrl == null){
            return ResponseEntity.notFound().build();
        }else{
           return ResponseEntity.status(HttpStatus.FOUND).location(URI.create( longUrl)).build();
        }
    }
}
