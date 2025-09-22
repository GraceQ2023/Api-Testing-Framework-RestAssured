package com.myorg.apitests.apis.booker;

import com.myorg.apitests.base.BaseTest;
import com.myorg.apitests.base.RequestSpecManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: CURDTest
 * Package: com.myorg.apitests.apis.booker
 * Description:
 *
 * @Author Grace
 * @Create 22/9/2025 9:49 am
 * Version 1.0
 */
public class CRUDTest extends BaseTest {

    private int bookingId;

    // Helper for building booking payload
    private Map<String, Object> createBookingPayload(String firstname, String lastname, int totalprice, boolean depositpaid,
                                                     String checkin, String checkout, String additionalneeds) {

        Map<String, Object> booking = new HashMap<>();
        booking.put("firstname", firstname);
        booking.put("lastname", lastname);
        booking.put("totalprice", totalprice);
        booking.put("depositpaid", depositpaid);

        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);

        booking.put("bookingdates", bookingDates);
        booking.put("additionalneeds", additionalneeds);

        return booking;
    }

    @Test (priority = 1)
    // POST request - create a new booking
    public void testCreateBooking(){
        Map<String, Object> payload = createBookingPayload(
                "James", "Brown", 111, true,
                "2025-01-01", "2025-01-07", "Breakfast");

        Response response = RestAssured
            .given()
                .spec(RequestSpecManager.getBookerSpec())
                .body(payload)
            .when()
                .post("/booking")
            .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("bookingid"));

        this.bookingId = response.jsonPath().getInt("bookingid");
    }

     @Test(priority = 2)
    // GET request - get booking by id
    public void testGetBookingById(){

        Response response = RestAssured
            .given()
                .spec(RequestSpecManager.getBookerSpec())
            .when()
                .get("/booking/" + bookingId)
            .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("James"));
    }

    @Test(priority = 3)
    // GET request - get all bookings list
    public void testGetAllBookings(){

        Response response = RestAssured
            .given()
                .spec(RequestSpecManager.getBookerSpec())
            .when()
                .get("/booking")
            .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        // Assert that response time is under the threshold
        Assert.assertTrue(response.getTime() < 2000,
                "Response time is too slow: " + response.getTime() + " ms");;
    }


    @Test(priority = 4)
    // PUT request - update a booking, include cookies for token
    public void testUpdateBooking(){

        Map<String, Object> payload = createBookingPayload(
                "Grace", "Qin", 888, true,
                "2025-9-22", "2025-9-23", "Yoga");

        Response response = RestAssured
            .given()
                .spec(RequestSpecManager.getBookerSpec())
//                .cookie("token", AuthTest.token)
                .header("Cookie", "token="+AuthTest.token)
                .body(payload)
            .when()
                .put("/booking/" + bookingId)
            .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("Yoga"));
    }

    @Test(priority = 5)
    // DELETE request - delete a booking, include cookies for token
    public void testDeleteBooking() {

        Response response = RestAssured
            .given()
                .spec(RequestSpecManager.getBookerSpec())
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
            .when()
                .delete("/booking/" + bookingId)
            .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 201);
    }
}


