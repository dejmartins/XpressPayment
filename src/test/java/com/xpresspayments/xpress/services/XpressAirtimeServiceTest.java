package com.xpresspayments.xpress.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class XpressAirtimeServiceTest {

    @Value("${xpress.private.key}")
    private String xpressPrivateKey;

    @Test
    void testCalculateHMAC512() {

        String purchaseAirtimeObject = "{\"uniqueCode\":\"MTN_24207\",\"requestId\":\"11332524\",\"details\":{\"amount\":100,\"phoneNumber\":\"08033333333\"}}";;

        String result = XpressAirtimeService.calculateHMAC512(purchaseAirtimeObject, xpressPrivateKey);

        assertNotNull(result);
    }

}