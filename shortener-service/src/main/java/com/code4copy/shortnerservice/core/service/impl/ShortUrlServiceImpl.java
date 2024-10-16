package com.code4copy.shortnerservice.core.service.impl;

import com.code4copy.shortnerservice.common.exception.ShorteningException;
import com.code4copy.shortnerservice.core.entity.ShortUrlMapEntity;
import com.code4copy.shortnerservice.core.repository.ShortUrlRepository;
import com.code4copy.shortnerservice.core.service.TokenRange;
import com.code4copy.shortnerservice.core.service.api.ShortUrlService;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ShortUrlServiceImpl implements ShortUrlService {
  private final ShortUrlRepository shortUrlRepository;
  private static final char[] DIGITS = ("0123456789"
      + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
      + "abcdefghijklmnopqrstuvwxyz").toCharArray();
  private static final long BASE = DIGITS.length;
  private static final long MAX_NUMBER = BASE * BASE * BASE * BASE * BASE * BASE * BASE;

  @Value("${token-service.endpoint}")
  private String tokenServiceEndPoint;
  @Value("${short-url.base}")
  private String baseUrl;

  private final RestTemplate restTemplate;
  private final RetryTemplate retryTemplate;
  private boolean fetchingTokenInProgress;
  private final ConcurrentLinkedQueue<Long> tokenQueue;
  private final StringRedisTemplate redisTemplate;
  private final CassandraAdminTemplate cassandraAdminTemplate;

  @Autowired
  public ShortUrlServiceImpl(final ShortUrlRepository shortUrlRepository,
                             final RestTemplate restTemplate,
                             final RetryTemplate retryTemplate,
                             final StringRedisTemplate redisTemplate,
                             final CassandraAdminTemplate cassandraAdminTemplate) {
    this.shortUrlRepository = shortUrlRepository;
    this.restTemplate = restTemplate;
    this.cassandraAdminTemplate = cassandraAdminTemplate;
    this.tokenQueue = new ConcurrentLinkedQueue<>();
    this.retryTemplate = retryTemplate;
    this.redisTemplate = redisTemplate;
  }

  @PostConstruct
  private void init() {
    this.cassandraAdminTemplate.createTable(true, ShortUrlMapEntity.class);
    this.updateTokenQueue();
  }

  @Override
  public String generateShortUrl(String longUrl) throws ShorteningException{

    CompletableFuture<Void> completableFuture = null;
    if (this.tokenQueue.size() < 200 && !this.fetchingTokenInProgress) {
      completableFuture = CompletableFuture.runAsync(this::updateTokenQueue);
    }
    if (this.tokenQueue.isEmpty() && completableFuture != null) {
      try {
        completableFuture.get(1000, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
        log.error(e.getMessage());
        Thread.currentThread().interrupt();
      } catch (ExecutionException e) {
        log.error(e.getMessage());
      } catch (TimeoutException e) {
        log.error(e.getMessage());
      }
    }
    if (this.tokenQueue.isEmpty()) {
      throw new ShorteningException("Token not available, some system error, please try again");
    } else {
      long token = this.tokenQueue.poll();
      String shortUrl = generateCode(token);
      ShortUrlMapEntity urlMapDO = new ShortUrlMapEntity();
      urlMapDO.setUrl(longUrl);
      urlMapDO.setShortUrl(shortUrl);
      urlMapDO.setCreatedOn(LocalDate.now());
      this.shortUrlRepository.save(urlMapDO);
      this.redisTemplate.opsForHash().put("shortUrl", shortUrl, longUrl);
      return baseUrl + shortUrl;
    }
  }

  private static String generateCode(long num) {
    if (num < 1 || num > MAX_NUMBER) {
      throw new IllegalArgumentException("Illegal input value: " + num);
    }

    char[] c = new char[7];
    for (int i = 0; i < 7; ++i) {
      c[i] = DIGITS[(int) (num % BASE)];
      num /= BASE;
    }
    return new String(c);
  }

  private void updateTokenQueue() {
    this.fetchingTokenInProgress = true;
    try {
      log.info("Token service endpoint: " + this.tokenServiceEndPoint);
      this.retryTemplate.execute(arg0 -> {
        TokenRange tokenRange =
            this.restTemplate.getForEntity(this.tokenServiceEndPoint, TokenRange.class).getBody();

        if (tokenRange == null) {
          throw new ShorteningException("Token range is null");
        }
        List<Long> randomList = new ArrayList<>();
        for (long i =  tokenRange.getFromNumber(); i <= tokenRange.getToNumber(); i++) {
          randomList.add(i);
        }
        Collections.shuffle(randomList);
        for (long i : randomList){
          this.tokenQueue.add(i);
        }
        return null;
      });
    } catch (RuntimeException e) {
      log.error(e.getMessage());
    }
    this.fetchingTokenInProgress = false;
  }


}
