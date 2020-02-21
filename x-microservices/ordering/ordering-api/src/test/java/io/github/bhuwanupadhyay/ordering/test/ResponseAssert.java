package io.github.bhuwanupadhyay.ordering.test;

import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.AbstractAssert;
import org.springframework.http.HttpStatus;

public class ResponseAssert extends AbstractAssert<ResponseAssert, ValidatableResponse> {

    public ResponseAssert(ValidatableResponse actual) {
        super(actual, ResponseAssert.class);
    }

    public ResponseAssert hasStatus(HttpStatus status) {
        actual.statusCode(status.value());
        return this;
    }

    public ResponseAssert isOk() {
        return hasStatus(HttpStatus.OK);
    }

    public ValidatableResponse extract() {
        return actual;
    }

}
