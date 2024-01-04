package com.xpresspayments.xpress.controllers;

import com.xpresspayments.xpress.dtos.requests.PurchaseAirtimeRequest;
import com.xpresspayments.xpress.dtos.responses.PurchaseAirtimeResponse;
import com.xpresspayments.xpress.services.AirtimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/airtime")
public class AirtimeController {

    private AirtimeService airtimeService;

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseAirtimeResponse> purchaseAirtime(@RequestBody PurchaseAirtimeRequest request){
        PurchaseAirtimeResponse purchaseAirtimeResponse = PurchaseAirtimeResponse.builder()
                .isSuccessful(true)
                .data(airtimeService.purchaseAirtime(request))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(purchaseAirtimeResponse);
    }
}
