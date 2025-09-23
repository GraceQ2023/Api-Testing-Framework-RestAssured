package com.myorg.apitests.apis.booker;

import com.myorg.apitests.base.BaseTest;
import com.myorg.apitests.base.RequestSpecManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.myorg.apitests.utils.ExtentManager;

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

    private static final Logger logger = LogManager.getLogger(CRUDTest.class); // logger for CRUDTTest class
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

    @Test (priority = 1, description = "Test POST request - create a new booking")
    // POST request - create a new booking
    public void testCreateBooking(){
        Map<String, Object> payload = createBookingPayload(
                "James", "Brown", 111, true,
                "2025-01-01", "2025-01-07", "Breakfast");

        logger.info("Creating new booking for James Brown");
        ExtentManager.logStep("Creating new booking for James Brown");

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

        bookingId = response.jsonPath().getInt("bookingid");
        logger.info("Booking created with ID: " + bookingId);
        ExtentManager.logStep("Booking created with ID: " + bookingId);
    }

     @Test(priority = 2, description = "Test GET request - get a booking by id")
    // GET request - get booking by id
    public void testGetBookingById(){
        logger.info("Fetching booking with ID: " + bookingId);
        ExtentManager.logStep("Fetching booking with ID: " + bookingId);

        Response response = RestAssured
            .given()
                .spec(RequestSpecManager.getBookerSpec())
            .when()
                .get("/booking/" + bookingId)
            .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("James"));
        logger.info("Booking details retrieved successfully");
        ExtentManager.logStep("Booking details retrieved successfully");
    }

    @Test(priority = 3, description = "Test GET request - get all bookings")
    // GET request - get all bookings list
    public void testGetAllBookings(){
        logger.info("Fetching all bookings details");
        ExtentManager.logStep("Fetching all bookings details");

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
                "Response time is too slow: " + response.getTime() + " ms");
        logger.info("List of all booking details is fetched.");
        ExtentManager.logStep("List of all bookings details is fetched.");
    }

    @Test(priority = 4, description = "Test PUT request - update a booking using cookies")
    // PUT request - update a booking, include cookies for token
    public void testUpdateBooking(){

        Map<String, Object> payload = createBookingPayload(
                "Grace", "Qin", 888, true,
                "2025-9-22", "2025-9-23", "Yoga");

        logger.info("Updating booking ID: " + bookingId);
        ExtentManager.logStep("Updating booking ID: " + bookingId);

        Response response = RestAssured
            .given()
                .spec(RequestSpecManager.getBookerSpec())
                .header("Cookie", "token="+AuthTest.token)
                .body(payload)
            .when()
                .put("/booking/" + bookingId)
            .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("Yoga"));
        logger.info("Booking updated successfully with new details");
        ExtentManager.logStep("Booking updated successfully with new details");
    }

    @Test(priority = 5, description = "Test DELETE request - delete a booking using Authorization")
    // DELETE request - delete a booking, include cookies for token
    public void testDeleteBooking() {

        logger.info("Deleting booking with ID: " + bookingId);
        ExtentManager.logStep("Deleting booking with ID: " + bookingId);

        Response response = RestAssured
            .given()
                .spec(RequestSpecManager.getBookerSpec())
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
            .when()
                .delete("/booking/" + bookingId)
            .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 201);
        logger.info("Booking with booking id: " + bookingId + " is deleted successfully");
        ExtentManager.logStep("Booking with booking id: " + bookingId + " is deleted successfully");
    }
}


