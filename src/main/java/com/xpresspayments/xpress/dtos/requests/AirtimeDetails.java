package com.xpresspayments.xpress.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AirtimeDetails {
    private String phoneNumber;
    private BigDecimal amount;
}
