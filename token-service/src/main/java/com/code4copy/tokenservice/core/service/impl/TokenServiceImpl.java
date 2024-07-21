package com.code4copy.tokenservice.core.service.impl;

import com.code4copy.tokenservice.core.repository.TokenLogRepository;
import com.code4copy.tokenservice.core.entity.TokenLogEntity;
import com.code4copy.tokenservice.core.service.api.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenLogRepository tokenLogRepository;
    @Value( "${token.size:1000}" )
    private long tokenSize =1000;
    @Autowired
    public TokenServiceImpl(final TokenLogRepository tokenLogRepository){
        this.tokenLogRepository = tokenLogRepository;
    }

    @Override
    public TokenLogEntity getNextTokenRange(){
        synchronized(this){
            long max = this.tokenLogRepository.maxToNumber();
            long from = max+1;
            long to = from+ tokenSize-1;
            TokenLogEntity tokenLogEntity =  new TokenLogEntity();
            tokenLogEntity.setRange(from, to);
            tokenLogEntity.setCreatedBy("");
            tokenLogEntity.setCreatedAt(Calendar.getInstance());
            return this.tokenLogRepository.save(tokenLogEntity);
        }
    }
}
