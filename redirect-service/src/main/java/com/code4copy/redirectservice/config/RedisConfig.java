package com.code4copy.redirectservice.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

  @Value("${spring.redis.database}")
  private int database;

  @Value("${spring.redis.host}")
  private String host;

  @Value("${spring.redis.port}")
  private int port;

  @Value("${spring.redis.timeout}")
  private int timeout;

  @Value("${spring.redis.password}")
  private String password;

  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration configuration =
        new RedisStandaloneConfiguration(this.host, this.port);
    configuration.setPassword(this.password);
    configuration.setDatabase(this.database);
    return new LettuceConnectionFactory(configuration);
  }

  @Bean
  public RedisCacheManager cacheManager(final RedisConnectionFactory connectionFactory) {
    return RedisCacheManager.create(connectionFactory);
  }

  @Bean
  public RedisCacheConfiguration cacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(60))
        .disableCachingNullValues()
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
            new GenericJackson2JsonRedisSerializer()));
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    StringRedisTemplate template = new StringRedisTemplate(factory);
    template.setValueSerializer(stringRedisSerializer);
    template.setHashKeySerializer(stringRedisSerializer);
    template.setHashValueSerializer(stringRedisSerializer);
    template.afterPropertiesSet();
    return template;
  }
}
