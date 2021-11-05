package com.code4copy.shortnerservice.core.domain;

import com.datastax.oss.driver.api.core.type.DataType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.Calendar;

@Table(value = "short_url_map")
@Getter
@Setter

@NoArgsConstructor
public class ShortUrlMapDO {
    @PrimaryKey
    @Column(value = "short_url")
    private String shortUrl;

    @Column(value = "url")
    private String url;

    private String createdBy;
    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate createdOn;
}
