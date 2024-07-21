package com.code4copy.tokenservice.adapter.api;

import com.code4copy.tokenservice.adapter.impl.TokenLogConverter;
import com.code4copy.tokenservice.core.entity.TokenLogEntity;
import com.code4copy.tokenservice.rest.resource.TokenLogResource;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ConverterTest {

  @Nested
  @DisplayName("Token Log converter test")
  class TokenLogConverterTest {
    private final TokenLogConverter tokenLogConverter = new TokenLogConverter();

    @Test
    @DisplayName("Test convert from resource to entity")
    void convertFromResource() {
      TokenLogResource tokenLogResource = Instancio.of(TokenLogResource.class).create();
      TokenLogResource tokenLogResource1 = this.tokenLogConverter.covertFromEntity(
          this.tokenLogConverter.convertFromResource(tokenLogResource));
      Assertions.assertEquals(tokenLogResource, tokenLogResource1);
    }

    @Test
    @DisplayName("Test convert from entity to resource")
    void covertFromEntity() {
      TokenLogEntity tokenLogEntity = Instancio.of(TokenLogEntity.class).create();
      TokenLogEntity tokenLogEntity1 = this.tokenLogConverter.convertFromResource(
          this.tokenLogConverter.covertFromEntity(tokenLogEntity));
      Assertions.assertEquals(tokenLogEntity, tokenLogEntity1);
    }

    @Test
    @DisplayName("Test convert from resource list to entity list")
    void createFromResources() {
      List<TokenLogResource> tokenLogResources =
          Instancio.ofList(TokenLogResource.class).size(5).create();
      List<TokenLogResource> tokenLogResources1 = this.tokenLogConverter.createFromEntities(
          this.tokenLogConverter.createFromResources(tokenLogResources));
      Assertions.assertTrue(tokenLogResources.size() == tokenLogResources1.size() &&
          tokenLogResources.containsAll(tokenLogResources1) &&
          tokenLogResources1.containsAll(tokenLogResources));
    }

    @Test
    @DisplayName("Test convert from entity list to resource list")
    void createFromEntities() {
      List<TokenLogEntity> tokenLogEntities =
          Instancio.ofList(TokenLogEntity.class).size(5).create();
      List<TokenLogEntity> tokenLogEntities1 = this.tokenLogConverter.createFromResources(
          this.tokenLogConverter.createFromEntities(tokenLogEntities));
      Assertions.assertTrue(tokenLogEntities.size() == tokenLogEntities1.size() &&
          tokenLogEntities.containsAll(tokenLogEntities1) &&
          tokenLogEntities1.containsAll(tokenLogEntities));
    }
  }
}