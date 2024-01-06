package com.xpresspayments.xpress.dtos.responses;

import com.xpresspayments.xpress.dtos.requests.AirtimeDetails;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PurchaseAirtimeResponse {
    private String requestId;
    private String referenceId;
    private String responseCode;
    private String responseMessage;
    private AirtimeDetails data;
}
