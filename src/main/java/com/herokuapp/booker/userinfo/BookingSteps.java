package com.herokuapp.booker.userinfo;

import com.herokuapp.booker.constants.EndPoints;
import com.herokuapp.booker.model.AuthPojo;
import com.herokuapp.booker.model.BookingPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class BookingSteps {

    static String username = "admin";
    static String password = "password123";

    static String name = "James";

    static int bookingid;

    @Step
    public ValidatableResponse createToken(String username, String password) {
        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername(username);
        authPojo.setPassword(password);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .when()
                .body(authPojo)
                .post(EndPoints.AUTHENTICATE)
                .then().log().all().statusCode(200);

    }
    @Step("Create booking with firstname : {0}, lastname : {1}, totalprice : {2}, depositpaid : {3}, additionalneeds : {4}")
    public ValidatableResponse createBooking(String token, String firstname,String lastname,int totalprice,boolean depositpaid,String additionalneeds){

        HashMap<String,String> checkInOutDatesData = new HashMap<String,String>();
        checkInOutDatesData.put("checkin","2018-01-01");
        checkInOutDatesData.put("checkout","2019-01-01");

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setAdditionalneeds(additionalneeds);
        bookingPojo.setBookingdates(checkInOutDatesData);

        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .header("cookie", token)
                .body(bookingPojo)
                .when()
                .post(EndPoints.CREATE_BOOKING_IDS)
                .then().log().all().statusCode(200);
    }
    @Step("Getting the booking information with the firstname : {0}")
    public ValidatableResponse getBookingByFirstName(int id){
        return SerenityRest.given().log().all()
                .pathParam("bookingId",id)
                .header("Accept", "*/*")
                .header("Connection","keep-alive")
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then().log().all()
                .statusCode(200);
    }

    @Step("Updating the booking information id : {0}, token : {1}, firstname : {2}, lastname : {3}, totalprice : {4}, depositpaid : {5}, additionalneeds : {6}")
    public ValidatableResponse updateBooking(int id, String token, String firstname,String lastname,int totalprice,boolean depositpaid,String additionalneeds){

        HashMap<String,String>checkInOutDatesData = new HashMap<String,String>();
        checkInOutDatesData.put("checkin","2018-01-01");
        checkInOutDatesData.put("checkout","2019-01-01");

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setAdditionalneeds(additionalneeds);
        bookingPojo.setBookingdates(checkInOutDatesData);

        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .header("Connection","keep-alive")
                .header("Cookie","token=cc618e0ec803b91")
                .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Accept", "*/*")
                .pathParam("bookingId",id)
                .when()
                .body(bookingPojo)
                .put(EndPoints.UPDATE_BOOKING_BY_ID)
                .then().log().all()
                .statusCode(200);
    }

    @Step("Deleting the booking information with bookingId : {0}")
    public ValidatableResponse deleteBooking(int id){

        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .header("Connection","keep-alive")
                .header("Cookie","token=cc618e0ec803b91")
                .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Accept", "*/*")
                .pathParam("bookingId",id)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then().log().all()
                .statusCode(201);
    }

    @Step("Getting booking information with bookingId : {0}")
    public ValidatableResponse getBookingById(int id){

        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .header("Connection","keep-alive")
                .header("Cookie","token=cc618e0ec803b91")
                .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Accept", "*/*")
                .pathParam("bookingId",id)
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then().log().all()
                .statusCode(404);
    }


}
