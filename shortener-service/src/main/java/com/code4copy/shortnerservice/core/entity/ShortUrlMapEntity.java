package com.code4copy.shortnerservice.core.entity;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "short_url_map")
@Getter
@Setter

@NoArgsConstructor
public class ShortUrlMapEntity {
    @PrimaryKey
    @Column(value = "short_url")
    @Id
    private String shortUrl;

    @Column(value = "url")
    private String url;

    private String createdBy;
    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate createdOn;
}
