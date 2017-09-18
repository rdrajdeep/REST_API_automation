package expertchat.bussinesslogic.paymentnonce;

// Created by Kishor on 5/10/2017.


import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateNonce {

    private static BraintreeGateway gateway = new BraintreeGateway(
            Environment.SANDBOX,
            "qctszrx63jy7pk3b",
            "byb6prd95qz7t8d9",
            "0456fa62901ac50310efd4c3d9f5459c"
    );

    // Connect to Braintree Gateway.
    public static BraintreeGateway connectBraintreeGateway() {

        return gateway;
    }

    public static String getPaymentMethodNonce() {

        String nonceFromTheClient =  "fake-consumed-nonce";
        return nonceFromTheClient;
    }
}
