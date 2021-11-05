package com.code4copy.shortnerservice.core.service.impl;

import com.code4copy.shortnerservice.core.dao.ShortUrlRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ShortUrlServiceImplTest {
    @InjectMocks
    ShortUrlServiceImpl shortUrlService;
    @Mock
    ShortUrlRepository shortUrlRepository;

    @Test
    public void test_ok(){
        String generateCode = ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 3521614606208L);
        assertEquals(generateCode, "0000000");
        generateCode = ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 1L);
        assertEquals(generateCode, "1000000");

        generateCode = ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 999l);
        assertEquals(generateCode, "7G00000");

        generateCode = ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 998009l);
        assertEquals(generateCode, "vcB4000");
        generateCode = ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 3521614606207L);
        assertEquals(generateCode, "zzzzzzz");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 0l);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ReflectionTestUtils.invokeMethod(shortUrlService, "generateCode", 3521614606209L);
        });
    }
}
