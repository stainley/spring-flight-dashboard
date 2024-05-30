package org.salapp.quarkusmq.springflightproducer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED)
public class FlightException extends RuntimeException {

    public FlightException(String message) {
        super(message);
    }
}
