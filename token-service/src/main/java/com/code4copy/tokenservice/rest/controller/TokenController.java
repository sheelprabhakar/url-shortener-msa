package com.code4copy.tokenservice.rest.controller;

import com.code4copy.tokenservice.adapter.RestAdapterV1;
import com.code4copy.tokenservice.rest.resource.TokenResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tokenservice/api/v1/")
public class TokenController {
    private final RestAdapterV1 restAdapterV1;
    @Autowired
    public TokenController(final RestAdapterV1 restAdapterV1){
        this.restAdapterV1 = restAdapterV1;
    }

    @GetMapping(path = "next/", produces = "application/json")
    public ResponseEntity<TokenResource>getNext(){
        TokenResource tokenResource = this.restAdapterV1.getNextTokenRange();
        return ResponseEntity.ok().body(tokenResource);
    }

}
