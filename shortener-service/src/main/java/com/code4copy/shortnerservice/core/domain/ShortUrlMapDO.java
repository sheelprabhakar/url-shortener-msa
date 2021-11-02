package com.code4copy.shortnerservice.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Calendar;

@Table(value = "short_url_map")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrlMapDO {
    @PrimaryKey
    @Column(value = "short_url")
    private String shortUrl;

    @Column(value = "url")
    private String url;

    private String createdBy;
    private Calendar createdOn;
}
