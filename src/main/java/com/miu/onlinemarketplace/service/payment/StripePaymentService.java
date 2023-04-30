package com.miu.onlinemarketplace.service.payment;

import com.stripe.model.Charge;

public interface StripePaymentService {
    Charge pay(String code, Double totalAmount);
}
