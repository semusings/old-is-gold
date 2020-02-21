package io.github.bhuwanupadhyay.ordering.test;

import io.github.bhuwanupadhyay.ordering.contract.IEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Scenario {

    private final int port;

    public static Scenario httpScenario(int port) {
        return new Scenario(port);
    }

    public ResponseAssert placeOrder(Json json) {
        return new ResponseAssert(
                http()
                        .body(json.toJsonString())
                        .post()
                        .then());
    }

    private RequestSpecification http() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri("http://localhost:" + port + "/" + IEndpoints.BASE_URI);
    }

}
