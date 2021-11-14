package com.code4copy.shortnerservice.core.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenRange {
    private long fromNumber;
    private long toNumber;
}