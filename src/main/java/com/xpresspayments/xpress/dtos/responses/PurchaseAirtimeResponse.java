package com.xpresspayments.xpress.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PurchaseAirtimeResponse {

    private boolean isSuccessful;
    private Object data;
}
