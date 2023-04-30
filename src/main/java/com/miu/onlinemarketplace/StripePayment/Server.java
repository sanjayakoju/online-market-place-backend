//package com.miu.onlinemarketplace.StripePayment;
//
//import java.nio.file.Paths;
//
//import static spark.Spark.post;
//import static spark.Spark.port;
//import static spark.Spark.staticFiles;
//
//import com.stripe.Stripe;
//import com.stripe.model.checkout.Session;
//import com.stripe.param.checkout.SessionCreateParams;
//
//public class Server {
//
//  public static void main(String[] args) {
//    port(4242);
//    // This is your test secret API key.
//    Stripe.apiKey = "sk_test_51Mwq8GIVB9DnC73sM6HIZtM0kA4oIm1oU1zaSIhql1J5c8aw5JtLzTOgcjzBDJooujfXmf2fF41WQgsIVgEGGclQ000nCP7qgs";
//    staticFiles.externalLocation(
//        Paths.get("public").toAbsolutePath().toString());
//    post("/create-checkout-session", (request, response) -> {
//        String YOUR_DOMAIN = "http://localhost:4242";
//        SessionCreateParams params =
//          SessionCreateParams.builder()
//            .setMode(SessionCreateParams.Mode.PAYMENT)
//            .setSuccessUrl(YOUR_DOMAIN + "/success.html")
//            .setCancelUrl(YOUR_DOMAIN + "/cancel.html")
//            .addLineItem(
//              SessionCreateParams.LineItem.builder()
//                .setQuantity(1L)
//                .setPrice("price_1Mz3gVIVB9DnC73s51uDfANe")
//                .build())
//            .build();
//      Session session = Session.create(params);
//      response.redirect(session.getUrl(), 303);
//      return "";
//    });
//  }
//}