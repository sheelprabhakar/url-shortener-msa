package com.code4copy.tokenservice.adapter;

import com.code4copy.tokenservice.rest.resource.TokenResource;

public interface RestAdapterV1 {
    TokenResource getNextTokenRange();
}
