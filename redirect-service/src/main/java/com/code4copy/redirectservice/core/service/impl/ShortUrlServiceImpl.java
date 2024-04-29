package com.code4copy.redirectservice.core.service.impl;

import com.code4copy.redirectservice.common.Constants;
import com.code4copy.redirectservice.core.dao.ShortUrlRepository;
import com.code4copy.redirectservice.core.domain.ShortUrlMapDO;
import com.code4copy.redirectservice.core.service.ShortUrlService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;
    private final StringRedisTemplate redisTemplate;
    private final CassandraAdminTemplate cassandraAdminTemplate;

    @Autowired
    public ShortUrlServiceImpl(final ShortUrlRepository shortUrlRepository,
                               final StringRedisTemplate redisTemplate, CassandraAdminTemplate cassandraAdminTemplate) {
        this.shortUrlRepository = shortUrlRepository;
        this.redisTemplate = redisTemplate;
        this.cassandraAdminTemplate = cassandraAdminTemplate;
    }

    @PostConstruct
    void initSchema() {
        this.cassandraAdminTemplate.createTable(true, ShortUrlMapDO.class);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        Object data = this.redisTemplate.opsForHash().entries(Constants.CACHE_KEY).get(shortUrl);//.get(Constants.CACHE_KEY, shortUrl);
        if (data == null) {
            Optional<ShortUrlMapDO> byId = this.shortUrlRepository.findById(shortUrl);
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
