package com.code4copy.shortnerservice.core.service;

import com.code4copy.shortnerservice.common.exception.ShorteningException;
import org.springframework.retry.annotation.Retryable;

public interface ShortUrlService {
    @Retryable(value = RuntimeException.class)
    String generateShortUrl(String longUrl) throws ShorteningException;
}
