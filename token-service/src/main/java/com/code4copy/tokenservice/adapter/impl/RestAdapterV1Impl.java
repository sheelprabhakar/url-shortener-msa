package com.code4copy.tokenservice.adapter.impl;

import com.code4copy.tokenservice.adapter.api.RestAdapterV1;
import com.code4copy.tokenservice.core.service.api.TokenService;
import com.code4copy.tokenservice.rest.resource.TokenLogResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestAdapterV1Impl implements RestAdapterV1 {
  private final TokenService urlTokenService;
  private final TokenLogConverter tokenLogConverter;

  @Autowired
  public RestAdapterV1Impl(final TokenService urlTokenService,
                           final TokenLogConverter tokenLogConverter) {
    this.urlTokenService = urlTokenService;
    this.tokenLogConverter = tokenLogConverter;
  }

  @Override
  public TokenLogResource getNextTokenRange() {
    return this.tokenLogConverter.covertFromEntity(this.urlTokenService.getNextTokenRange());
  }
}
