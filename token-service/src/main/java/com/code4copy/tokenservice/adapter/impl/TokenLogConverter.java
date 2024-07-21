package com.code4copy.tokenservice.adapter.impl;

import com.code4copy.tokenservice.adapter.api.Converter;
import com.code4copy.tokenservice.core.entity.TokenLogEntity;
import com.code4copy.tokenservice.rest.resource.TokenLogResource;
import org.springframework.stereotype.Component;

/**
 * The type Token log converter.
 */
@Component("tokenLogConverter")
public final class TokenLogConverter extends Converter<TokenLogEntity, TokenLogResource> {

  /**
   * Instantiates a new Token log converter.
   */
  public TokenLogConverter(){
    super( TokenLogConverter::convertToEntity, TokenLogConverter::convertToResource);
  }
  private static TokenLogEntity convertToEntity(TokenLogResource resource){
    return new TokenLogEntity(resource.getId(), resource.getFromNumber(), resource.getToNumber(),
       resource.getCreatedAt(), resource.getCreatedBy() );
  }

  private static TokenLogResource convertToResource(TokenLogEntity entity){
    return new TokenLogResource(entity.getId(), entity.getFromNumber(), entity.getToNumber(),
        entity.getCreatedAt(), entity.getCreatedBy() );
  }
}
