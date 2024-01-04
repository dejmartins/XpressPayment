package com.xpresspayments.xpress.controllers;

import com.xpresspayments.xpress.dtos.requests.PurchaseAirtimeRequest;
import com.xpresspayments.xpress.dtos.responses.PurchaseAirtimeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/airtime")
public class AirtimeController {

    @PostMapping()
    public ResponseEntity<PurchaseAirtimeResponse> purchaseAirtime(PurchaseAirtimeRequest request){
        PurchaseAirtimeResponse purchaseAirtimeResponse = PurchaseAirtimeResponse.builder()
                .isSuccessful(true)
                .data("Correct")
                .build();

        return new ResponseEntity<>(purchaseAirtimeResponse, HttpStatus.OK);
    }
}
