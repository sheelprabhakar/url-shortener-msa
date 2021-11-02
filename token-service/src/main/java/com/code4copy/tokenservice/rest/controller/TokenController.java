package com.code4copy.tokenservice.rest.controller;

import com.code4copy.tokenservice.adapter.RestAdapterV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/V1/")
public class TokenController {
    private final RestAdapterV1 restAdapterV1;
    @Autowired
    public TokenController(final RestAdapterV1 restAdapterV1){
        this.restAdapterV1 = restAdapterV1;
    }
}
