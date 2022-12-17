package de.trundicho.onion.billing.application;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
class MockResponse {

    HttpStatus httpStatus = HttpStatus.OK; // Default
    String responseJsonFile;

}
