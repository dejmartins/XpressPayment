package com.xpresspayments.xpress.controllers;

import com.xpresspayments.xpress.dtos.requests.PurchaseAirtimeRequest;
import com.xpresspayments.xpress.dtos.responses.PurchaseAirtimeResponse;
import com.xpresspayments.xpress.dtos.responses.SuccessResponse;
import com.xpresspayments.xpress.services.XpressAirtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/airtime")
@RequiredArgsConstructor
public class AirtimeController {

    private final XpressAirtimeService airtimeService;

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseAirtime(@RequestBody PurchaseAirtimeRequest request) throws IOException {
        SuccessResponse response = SuccessResponse.builder()
                .data(airtimeService.purchaseAirtime(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
