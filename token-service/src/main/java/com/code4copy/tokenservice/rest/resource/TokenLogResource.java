package com.code4copy.tokenservice.rest.resource;

import java.io.Serializable;
import java.util.Calendar;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TokenLogResource implements Serializable {
    private int id;
    private long fromNumber;
    private long toNumber;
    private Calendar createdAt;
    private String createdBy;
}
