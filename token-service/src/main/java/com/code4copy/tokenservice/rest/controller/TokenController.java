package com.code4copy.tokenservice.rest.controller;

import com.code4copy.tokenservice.adapter.api.RestAdapterV1;
import com.code4copy.tokenservice.rest.resource.TokenLogResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/")
public class TokenController {
    private final RestAdapterV1 restAdapterV1;
    @Autowired
    public TokenController(final RestAdapterV1 restAdapterV1){
        this.restAdapterV1 = restAdapterV1;
    }

    @GetMapping(path = "next/", produces = "application/json")
    public ResponseEntity<TokenLogResource>getNext(){
        TokenLogResource tokenLogResource = this.restAdapterV1.getNextTokenRange();
        return ResponseEntity.ok().body(tokenLogResource);
    }

}
