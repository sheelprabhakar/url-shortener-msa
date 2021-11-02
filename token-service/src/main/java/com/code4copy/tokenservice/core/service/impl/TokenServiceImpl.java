package com.code4copy.tokenservice.core.service.impl;

import com.code4copy.tokenservice.core.dao.TokenLogRepository;
import com.code4copy.tokenservice.core.domain.TokenLogDO;
import com.code4copy.tokenservice.core.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenLogRepository tokenLogRepository;
    @Value( "${token.size:1000}" )
    private long tokeSize=1000;
    @Autowired
    public TokenServiceImpl(final TokenLogRepository tokenLogRepository){
        this.tokenLogRepository = tokenLogRepository;
    }

    @Override
    public  TokenLogDO getNextTokenRange(){
        synchronized(this){
            long max = this.tokenLogRepository.maxToNumber();
            long from = max+1;
            long to = from+tokeSize;
            TokenLogDO tokenLogDO =  new TokenLogDO();
            tokenLogDO.setRange(from, to);
            tokenLogDO.setCreatedBy("");
            tokenLogDO.setCreateAt(Calendar.getInstance());
            return this.tokenLogRepository.save(tokenLogDO);
        }
    }
}
