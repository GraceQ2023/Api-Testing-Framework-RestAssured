package com.myorg.apitests.apis.booker;
import org.apache.logging.log4j.LogManager;
import com.myorg.apitests.base.BaseTest;
import com.myorg.apitests.base.RequestSpecManager;
import com.myorg.apitests.dataproviders.TestDataProvider;
import com.myorg.apitests.utils.ExtentManager;
import io.restassured.RestAssured;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AuthTest
 * Package: com.myorg.apitests.apis.booker
 * Description:
 *
 * @Author Grace
 * @Create 19/9/2025 10:59 am
 * Version 1.0
 */
public class AuthTest extends BaseTest {

    /**
     * Data-driven login tests using Excel data
     * Excel Columns: username | password | expectedStatus | expectedKey
     */

    private static final Logger logger = LogManager.getLogger(AuthTest.class); // logger for AuthTest class
    public static String token; // shared token for other tests

    @Test(dataProvider= "loginData", dataProviderClass = TestDataProvider.class, description = "Test login with valid and invalid credentials. ")
    public void testLogin(String username, String password, String expectedStatusCode, String expectedKey){

        // build JSON body from DataProvider
        Map<String, String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);

        logger.info("Sending login request with username: " + username + ", password: " + password);
        ExtentManager.logStep("Sending login request with username: " + username + ", password: " + password);

        Response response = RestAssured
            .given()
                    .spec(RequestSpecManager.getBookerSpec())
                    .body(payload)
            .when()
                    .post("/auth")
            .then()
                    .extract().response();

        int actualStatusCode = response.getStatusCode();
        String responseBody = response.asString();


        // Assert status code
        Assert.assertEquals(actualStatusCode, Integer.parseInt(expectedStatusCode), "Status code mismatch for user: " + username);

        // Check response key
        if("token".equalsIgnoreCase(expectedKey)){
            Assert.assertTrue(responseBody.contains("token"),
                    "Expected token in response body but got: " + responseBody);

            token = response.jsonPath().getString("token"); // dave token for later tests
            logger.info("Generated token: " + token);
            ExtentManager.logStep("Generated token: " + token);

        }else if ("reason".equalsIgnoreCase(expectedKey)){
            Assert.assertTrue(responseBody.contains("reason"), "Expected reason in response body but got: " + responseBody);
            logger.info("Invalid login verified - reason returned as expected");
            ExtentManager.logStep("Invalid login verified - reason shown as expected");
        }
    }

}

