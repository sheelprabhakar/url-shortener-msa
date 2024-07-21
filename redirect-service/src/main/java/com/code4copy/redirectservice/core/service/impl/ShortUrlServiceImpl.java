package com.code4copy.redirectservice.core.service.impl;

import com.code4copy.redirectservice.common.Constants;
import com.code4copy.redirectservice.core.entity.ShortUrlMapEntity;
import com.code4copy.redirectservice.core.repository.ShortUrlRepository;
import com.code4copy.redirectservice.core.service.api.ShortUrlService;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
  private final ShortUrlRepository shortUrlRepository;
  private final StringRedisTemplate redisTemplate;
  private final CassandraAdminTemplate cassandraAdminTemplate;

  @Autowired
  public ShortUrlServiceImpl(final ShortUrlRepository shortUrlRepository,
                             final StringRedisTemplate redisTemplate,
                             CassandraAdminTemplate cassandraAdminTemplate) {
    this.shortUrlRepository = shortUrlRepository;
    this.redisTemplate = redisTemplate;
    this.cassandraAdminTemplate = cassandraAdminTemplate;
  }

  @PostConstruct
  void initSchema() {
    this.cassandraAdminTemplate.createTable(true, ShortUrlMapEntity.class);
  }

  @Override
  public String getLongUrl(String shortUrl) {
    Object data = this.redisTemplate.opsForHash().entries(Constants.CACHE_KEY).get(shortUrl);
    if (data == null) {
      Optional<ShortUrlMapEntity> byId = this.shortUrlRepository.findById(shortUrl);
      if (byId.isPresent()) {
        data = byId.get().getUrl();
        this.redisTemplate.opsForHash().put(Constants.CACHE_KEY, shortUrl, data);
      }
    }
    if (data != null) {
      return (String) data;
    } else {
      return null;
    }
  }

}
