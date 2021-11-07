package com.code4copy.redirectservice.core.service.impl;

import com.code4copy.redirectservice.common.Constants;
import com.code4copy.redirectservice.core.dao.ShortUrlRepository;
import com.code4copy.redirectservice.core.domain.ShortUrlMapDO;
import com.code4copy.redirectservice.core.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;
       private final StringRedisTemplate redisTemplate;

    @Autowired
    public ShortUrlServiceImpl(final ShortUrlRepository shortUrlRepository,
                               final StringRedisTemplate redisTemplate){
        this.shortUrlRepository = shortUrlRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getLongUrl(String shortUrl){
        Object data =  this.redisTemplate.opsForHash().entries(Constants.CACHE_KEY).get(shortUrl);//.get(Constants.CACHE_KEY, shortUrl);
        if(data == null){
            Optional<ShortUrlMapDO> byId = this.shortUrlRepository.findById(shortUrl);
            if(byId.isPresent()){
                data = byId.get().getUrl();
                this.redisTemplate.opsForHash().put(Constants.CACHE_KEY, shortUrl, data);
            }
        }
        if(data != null) {
            return (String) data;
        }else{
            return null;
        }
    }

}
