package com.xpresspayments.xpress.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import com.xpresspayments.xpress.dtos.requests.AirtimeDetails;
import com.xpresspayments.xpress.dtos.requests.PurchaseAirtimeRequest;
import com.xpresspayments.xpress.dtos.responses.PurchaseAirtimeResponse;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.xpresspayments.xpress.utils.AppUtils.*;

@Service
public class XpressAirtimeService implements AirtimeService {

    @Value("${xpress.public.key}")
    private String xpressPublicKey;
    @Value("${xpress.private.key}")
    private String xpressPrivateKey;

    public PurchaseAirtimeResponse purchaseAirtime(PurchaseAirtimeRequest purchaseAirtimeRequest) throws IOException {
        validatePurchaseRequest(purchaseAirtimeRequest);


        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        JSONObject jsonPurchaseAirtimeRequest = getJsonPurchaseAirtimeRequest(purchaseAirtimeRequest);

        RequestBody requestBody = RequestBody.create(mediaType, jsonPurchaseAirtimeRequest.toString());

        String PaymentHash = calculateHMAC512(jsonPurchaseAirtimeRequest.toString(), xpressPrivateKey);

        Request request = new Request.Builder()
                .url(AIRTIME_API_URL)
                .post(requestBody)
                .addHeader(CONTENT_TYPE_HEADER, "application/json")
                .addHeader(AUTHORIZATION_HEADER, BEARER_TOKEN_PREFIX + xpressPublicKey)
                .addHeader(PAYMENT_HASH_HEADER, PaymentHash)
                .addHeader(CHANNEL_HEADER, "API")
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        JSONObject responseBodyAsJson = new JSONObject(responseBody);

        if (!response.isSuccessful()) {
//          Throw an IOException with the error message from the response.
            throw new IOException(responseBodyAsJson.getString("responseMessage"));
        }

        String dataValue = responseBodyAsJson.get("data").toString();

        AirtimeDetails airtimeDetails = mapDataToAirtimeDetails(dataValue);

        return PurchaseAirtimeResponse.builder()
                .referenceId(responseBodyAsJson.getString("referenceId"))
                .requestId(responseBodyAsJson.getString("requestId"))
                .responseCode(responseBodyAsJson.getString("responseCode"))
                .responseMessage(responseBodyAsJson.getString("responseMessage"))
                .data(airtimeDetails)
                .build();
    }

    private AirtimeDetails mapDataToAirtimeDetails(String dataValue) {
        JSONObject airtimeDetailsJson = new JSONObject(dataValue);

        return AirtimeDetails.builder()
                .amount(airtimeDetailsJson.getInt("amount"))
                .phoneNumber(airtimeDetailsJson.getString("phoneNumber"))
                .build();
    }

    private static JSONObject getJsonPurchaseAirtimeRequest(PurchaseAirtimeRequest purchaseAirtimeRequest) {
        JSONObject details = new JSONObject();
        details.put("phoneNumber", purchaseAirtimeRequest.getDetails().getPhoneNumber());
        details.put("amount", purchaseAirtimeRequest.getDetails().getAmount());

        JSONObject jsonPurchaseAirtimeRequest = new JSONObject();
        jsonPurchaseAirtimeRequest.put("requestId", purchaseAirtimeRequest.getRequestId());
        jsonPurchaseAirtimeRequest.put("uniqueCode", purchaseAirtimeRequest.getUniqueCode());
        jsonPurchaseAirtimeRequest.put("details", details);
        return jsonPurchaseAirtimeRequest;
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

        if (purchaseAirtimeRequest.getDetails().getAmount() <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10,}");
    }

    public static String calculateHMAC512(String data, String key) {
        String HMAC_SHA512 = "HmacSHA512";

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);

        Mac mac = null;

        try {
            mac = Mac.getInstance(HMAC_SHA512);

            mac.init(secretKeySpec);

            return Hex.encodeHexString(mac.doFinal(data.getBytes()));

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {

            throw new RuntimeException(e.getMessage());

        }

    }

}
