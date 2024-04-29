package com.code4copy.tokenservice.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Table(name = "token_log")
@Entity(name = "token_log")
@Getter
@Setter
public class TokenLogDO {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private int id;
    @Column(nullable = false, updatable = false, unique = true)
    private long fromNumber;
    @Column(nullable = false, updatable = false, unique = true)
    private long toNumber;

    @Column(nullable = false, updatable = false)
    private Calendar createAt;

    @Column(length = 255, nullable = true, updatable = false)
    private String createdBy;

    public void setRange(long from, long to){
        if(from > to){
            throw new IllegalArgumentException("From can not be greater than to.");
        }
        this.fromNumber = from;
        this.toNumber = to;
    }
}
