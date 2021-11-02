package com.code4copy.shortnerservice.core.service.impl;

import com.code4copy.shortnerservice.core.dao.ShortUrlRepository;
import com.code4copy.shortnerservice.core.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;
    private static final char[] DIGITS = ("0123456789"
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz").toCharArray();
    private static final long BASE = DIGITS.length;
    private static final long MAX_NUMBER = BASE * BASE * BASE * BASE * BASE * BASE * BASE ;

    @Autowired
    public ShortUrlServiceImpl(final ShortUrlRepository shortUrlRepository){
        this.shortUrlRepository = shortUrlRepository;
    }

    private static String generateCode(long num) {
        if (num < 1 || num > MAX_NUMBER) {
            throw new IllegalArgumentException("Illegal input value: " + num);
        }

        char[]c = new char[7];
        for(int i =0; i < 7; ++i){
            c[i]=DIGITS[(int)(num%BASE)];
            num /= BASE;
        }
        return new String(c);
    }
}
