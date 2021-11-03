package com.code4copy.tokenservice.core.dao;

import com.code4copy.tokenservice.core.domain.TokenLogDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TokenLogRepository extends CrudRepository<TokenLogDO, Integer> {
    @Query(value = "SELECT coalesce(max(toNumber),0) FROM token_log")
    public Long maxToNumber();
}