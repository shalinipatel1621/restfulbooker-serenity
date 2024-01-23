package com.herokuapp.booker.bookininfo;

import com.herokuapp.booker.constants.EndPoints;
import com.herokuapp.booker.model.AuthPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

public class UserSteps {


    @Step("Creating Token")
    public ValidatableResponse createToken(String username, String password) {
        AuthPojo authorisationPojo = new AuthPojo();
        authorisationPojo.setUsername(username);
        authorisationPojo.setPassword(password);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(authorisationPojo)
                .post(EndPoints.AUTHENTICATE)
                .then();


    }
}
