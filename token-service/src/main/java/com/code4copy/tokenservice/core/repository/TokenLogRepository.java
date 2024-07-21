package com.code4copy.tokenservice.core.repository;

import com.code4copy.tokenservice.core.entity.TokenLogEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


@Transactional
public interface TokenLogRepository extends CrudRepository<TokenLogEntity, Integer> {
    @Query(value = "SELECT coalesce(max(toNumber),0) FROM token_log")
    Long maxToNumber();
}
