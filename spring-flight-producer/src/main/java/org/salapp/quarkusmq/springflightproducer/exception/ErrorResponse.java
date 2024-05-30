package org.salapp.quarkusmq.springflightproducer.exception;


import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class ErrorResponse {

    private int statusCode;
    private String message;
    private LocalDateTime timestamp;

    public static class Builder {
        private int statusCode;
        private String message;
        private final LocalDateTime timestamp = LocalDateTime.now();

        public Builder withStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponse build() {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.statusCode = statusCode;
            errorResponse.message = message;
            errorResponse.timestamp = timestamp;

            return errorResponse;
        }
    }

}
