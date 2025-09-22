package com.myorg.apitests.apis.booker;

import com.myorg.apitests.base.BaseTest;
import com.myorg.apitests.base.RequestSpecManager;
import com.myorg.apitests.dataproviders.TestDataProvider;
import io.restassured.RestAssured;
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

    // shared token for other tests
    public static String token;

    @Test(dataProvider= "loginData", dataProviderClass = TestDataProvider.class)
    public void testLogin(String username, String password, String expectedStatusCode, String expectedKey){

        // build JSON body from DataProvider
        Map<String, String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);

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
            // save token for later tests
            token = response.jsonPath().getString("token");

        }else if ("reason".equalsIgnoreCase(expectedKey)){
            Assert.assertTrue(responseBody.contains("reason"), "Expected reason in response body but got: " + responseBody);
        }
    }

}

