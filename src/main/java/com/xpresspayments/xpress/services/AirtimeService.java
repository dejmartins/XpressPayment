package com.xpresspayments.xpress.services;

import com.xpresspayments.xpress.dtos.requests.PurchaseAirtimeRequest;
import com.xpresspayments.xpress.dtos.responses.PurchaseAirtimeResponse;

import java.io.IOException;

public interface AirtimeService {

    PurchaseAirtimeResponse purchaseAirtime(PurchaseAirtimeRequest request) throws IOException;
}
