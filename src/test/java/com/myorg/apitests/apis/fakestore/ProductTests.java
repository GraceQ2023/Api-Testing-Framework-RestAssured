package com.myorg.apitests.apis.fakestore;

import com.myorg.apitests.base.BaseTest;
import com.myorg.apitests.base.RequestSpecManager;
import io.restassured.RestAssured;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

/**
 * ClassName: ProductTests
 * Package: com.myorg.apitests.apis.fakestore
 * Description:
 *
 * @Author Grace
 * @Create 18/9/2025 3:11 pm
 * Version 1.0
 */
public class ProductTests extends BaseTest {

    @Test
    public void testGetAllProducts(){

        given()
            .spec(RequestSpecManager.getFakeStoreSpec())
        .when()
            .get("/products")
        .then()
            .statusCode(200);
    }




}
