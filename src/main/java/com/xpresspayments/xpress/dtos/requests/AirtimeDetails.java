package com.xpresspayments.xpress.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class AirtimeDetails {
    private String phoneNumber;
    private int amount;
}
