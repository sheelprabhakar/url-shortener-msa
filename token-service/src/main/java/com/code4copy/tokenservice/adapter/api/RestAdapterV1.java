package com.code4copy.tokenservice.adapter.api;

import com.code4copy.tokenservice.rest.resource.TokenLogResource;

public interface RestAdapterV1 {
    TokenLogResource getNextTokenRange();
}
