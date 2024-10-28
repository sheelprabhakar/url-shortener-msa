package com.code4copy.shortnerservice.core.repository;


import com.code4copy.shortnerservice.core.entity.ShortUrlMapEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends CassandraRepository<ShortUrlMapEntity, String> {

}
