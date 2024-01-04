package com.xpresspayments.xpress.dtos.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PurchaseAirtimeRequest {
    private String requestId;
    private String uniqueCode;
    private AirtimeDetails details;
}
