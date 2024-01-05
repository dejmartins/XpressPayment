package com.xpresspayments.xpress.services;

import com.squareup.okhttp.*;
import com.xpresspayments.xpress.dtos.requests.PurchaseAirtimeRequest;
import com.xpresspayments.xpress.dtos.responses.PurchaseAirtimeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.xpresspayments.xpress.utils.AppUtils.*;

@Service
public class AirtimeService {

    @Value("${xpress.public.key}")
    private String xpressPublicKey;

    public PurchaseAirtimeResponse purchaseAirtime(PurchaseAirtimeRequest purchaseAirtimeRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        String jsonBody = String.format("{\"requestId\": %s, \"uniqueCode\": \"%s\", \"details\": {\"phoneNumber\": \"%s\", \"amount\": %s}}",
                purchaseAirtimeRequest.getRequestId(),
                purchaseAirtimeRequest.getUniqueCode(),
                purchaseAirtimeRequest.getDetails().getPhoneNumber(),
                purchaseAirtimeRequest.getDetails().getAmount());

        RequestBody requestBody = RequestBody.create(mediaType, jsonBody);

        Request request = new Request.Builder()
                .url(AIRTIME_API_URL)
                .post(requestBody)
                .addHeader(CONTENT_TYPE_HEADER, "application/json")
                .addHeader(AUTHORIZATION_HEADER, BEARER_TOKEN_PREFIX + xpressPublicKey)
                .addHeader(PAYMENT_HASH_HEADER, "GENERATED_HMAC")
                .addHeader(CHANNEL_HEADER, "API")
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        return PurchaseAirtimeResponse.builder()
                .isSuccessful(response.isSuccessful())
                .data(responseBody)
                .build();

    }
}
