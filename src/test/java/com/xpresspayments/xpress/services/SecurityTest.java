package com.xpresspayments.xpress.services;

import com.xpresspayments.xpress.dtos.requests.AirtimeDetails;
import com.xpresspayments.xpress.dtos.requests.LoginRequest;
import com.xpresspayments.xpress.dtos.requests.PurchaseAirtimeRequest;
import com.xpresspayments.xpress.security.services.XpressJwtService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class SecurityTest {

    @Autowired
    private XpressJwtService xpressJwtService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAccessAirtimePurchaseEndpointWithValidToken() {
        String validToken = getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(validToken);

        PurchaseAirtimeRequest purchaseAirtimeRequest = new PurchaseAirtimeRequest();

        AirtimeDetails airtimeDetails = AirtimeDetails.builder()
                .phoneNumber("08033333333")
                .amount(100)
                .build();

        purchaseAirtimeRequest.setRequestId("1234569");
        purchaseAirtimeRequest.setUniqueCode("MTN_24207");
        purchaseAirtimeRequest.setDetails(airtimeDetails);

        HttpEntity<PurchaseAirtimeRequest> request = new HttpEntity<>(purchaseAirtimeRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/api/airtime/purchase", HttpMethod.POST, request, String.class);

        assertNotEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    }

    @Test
    public void testAccessSecuredEndpointWithoutToken() {
        HttpHeaders headers = new HttpHeaders();

        PurchaseAirtimeRequest purchaseAirtimeRequest = new PurchaseAirtimeRequest();

        AirtimeDetails airtimeDetails = AirtimeDetails.builder()
                .phoneNumber("08033333333")
                .amount(100)
                .build();

        purchaseAirtimeRequest.setRequestId("1234569");
        purchaseAirtimeRequest.setUniqueCode("MTN_24207");
        purchaseAirtimeRequest.setDetails(airtimeDetails);

        HttpEntity<PurchaseAirtimeRequest> request = new HttpEntity<>(purchaseAirtimeRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/api/airtime/purchase", HttpMethod.POST, request, String.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    }

    private String getToken() {
        return xpressJwtService.generateTokenFor("jerry@email.com");
    }
}
