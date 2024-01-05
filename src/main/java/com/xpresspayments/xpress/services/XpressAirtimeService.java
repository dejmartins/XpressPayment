package com.xpresspayments.xpress.services;

import com.squareup.okhttp.*;
import com.xpresspayments.xpress.dtos.requests.PurchaseAirtimeRequest;
import com.xpresspayments.xpress.dtos.responses.PurchaseAirtimeResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

import static com.xpresspayments.xpress.utils.AppUtils.*;

@Service
public class XpressAirtimeService implements AirtimeService {

    @Value("${xpress.public.key}")
    private String xpressPublicKey;

    public PurchaseAirtimeResponse purchaseAirtime(PurchaseAirtimeRequest purchaseAirtimeRequest) throws IOException {
        validatePurchaseRequest(purchaseAirtimeRequest);

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

//      Create a JSON object from the response body.
        JSONObject jsonObject= new JSONObject(responseBody);

        if (!response.isSuccessful()) {
//          Throw an IOException with the error message from the response.
            throw new IOException(jsonObject.getString("responseMessage"));
        }

        return PurchaseAirtimeResponse.builder()
                .referenceId(jsonObject.getString("referenceId"))
                .requestId(jsonObject.getString("requestId"))
                .responseCode(jsonObject.getString("responseCode"))
                .responseMessage(jsonObject.getString("responseMessage"))
                .data(jsonObject.getString("data"))
                .build();
    }

    private void validatePurchaseRequest(PurchaseAirtimeRequest purchaseAirtimeRequest) {
        if (purchaseAirtimeRequest.getRequestId() == null || purchaseAirtimeRequest.getRequestId().isEmpty()) {
            throw new IllegalArgumentException("Invalid requestId");
        }

        if (purchaseAirtimeRequest.getUniqueCode() == null || purchaseAirtimeRequest.getUniqueCode().isEmpty()) {
            throw new IllegalArgumentException("Invalid uniqueCode");
        }

        if (purchaseAirtimeRequest.getDetails() == null) {
            throw new IllegalArgumentException("Details cannot be null");
        }

        if (purchaseAirtimeRequest.getDetails().getPhoneNumber() == null ||
                !isValidPhoneNumber(purchaseAirtimeRequest.getDetails().getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phoneNumber");
        }

        if (purchaseAirtimeRequest.getDetails().getAmount() == null || purchaseAirtimeRequest.getDetails().getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10,}");
    }
}
