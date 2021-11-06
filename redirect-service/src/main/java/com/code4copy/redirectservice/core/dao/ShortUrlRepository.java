package com.code4copy.redirectservice.core.dao;


import com.code4copy.redirectservice.core.domain.ShortUrlMapDO;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends CassandraRepository<ShortUrlMapDO, String> {

}
