package com.code4copy.shortnerservice.repository;

import com.code4copy.shortnerservice.model.ShortUrlMap;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ShortUrlRepository extends CassandraRepository<ShortUrlMap, String> {

}
