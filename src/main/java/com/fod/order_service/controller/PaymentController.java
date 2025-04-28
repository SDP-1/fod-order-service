package com.fod.order_service.controller;

import com.fod.order_service.entity.PaymentRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, Object>> createCheckoutSession(@RequestBody PaymentRequest paymentRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Create Checkout Session
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:5173/success")
                    .setCancelUrl("http://localhost:5173/fod-order/order-summary/"+paymentRequest.getUserId())
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(paymentRequest.getCurrency())
                                                    .setUnitAmount((long) paymentRequest.getAmount())
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(paymentRequest.getDescription())
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .setQuantity(1L)
                                    .build()
                    )
                    .build();

            Session session = Session.create(params);
            System.out.println("Created Checkout Session: " + session.getId());

            response.put("sessionId", session.getId());
            response.put("sessionUrl", session.getUrl());
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
            response.put("status", "failed");
            return ResponseEntity.status(500).body(response);
        }
    }
}