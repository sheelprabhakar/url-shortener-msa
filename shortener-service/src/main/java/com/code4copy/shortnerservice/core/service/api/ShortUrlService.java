package com.code4copy.shortnerservice.core.service.api;

import com.code4copy.shortnerservice.common.exception.ShorteningException;
import org.springframework.retry.annotation.Retryable;

public interface ShortUrlService {
    @Retryable(retryFor = RuntimeException.class)
    String generateShortUrl(String longUrl) throws ShorteningException;
}
