package com.code4copy.shortnerservice.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.code4copy.shortnerservice.core.repository.ShortUrlRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ShortUrlServiceImplTest {
  @InjectMocks
  ShortUrlServiceImpl shortUrlService;
  @Mock
  ShortUrlRepository shortUrlRepository;

  @Test
  void test_ok() {
    String generateCode =
        ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 3521614606208L);
    assertEquals("0000000", generateCode);
    generateCode = ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 1L);
    assertEquals("1000000", generateCode);

    generateCode = ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 999L);
    assertEquals("7G00000", generateCode);

    generateCode = ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 998009L);
    assertEquals("vcB4000", generateCode);
    generateCode =
        ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 3521614606207L);
    assertEquals("zzzzzzz", generateCode);

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 0L);
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 3521614606209L);
    });
  }
}
