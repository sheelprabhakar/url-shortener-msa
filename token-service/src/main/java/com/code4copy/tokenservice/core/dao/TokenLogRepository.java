package com.code4copy.tokenservice.core.dao;

import com.code4copy.tokenservice.core.domain.TokenLogDO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


@Transactional
public interface TokenLogRepository extends CrudRepository<TokenLogDO, Integer> {
    @Query(value = "SELECT coalesce(max(toNumber),0) FROM token_log")
    Long maxToNumber();
}
