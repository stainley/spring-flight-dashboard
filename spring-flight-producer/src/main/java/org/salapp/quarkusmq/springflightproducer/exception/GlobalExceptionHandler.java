package org.salapp.quarkusmq.springflightproducer.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles FlightException and constructs an ErrorResponse.
     *
     * @param ex      the FlightException thrown
     * @param request the HttpServletRequest in which the exception occurred
     * @return a ResponseEntity containing the ErrorResponse and HttpStatus.BAD_REQUEST
     */
    @ExceptionHandler(FlightException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleFlightException(final FlightException ex, final HttpServletRequest request) {

       log.error("Flight exception occurred: {}: {}", request.getRequestURI(),ex.getMessage());

        setRequestAttributes(request, ex);

        ErrorResponse errorResponse = new ErrorResponse
                .Builder()
                .withMessage(ex.getMessage())
                .withStatusCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Sets the request attributes for error details.
     *
     * @param request the HttpServletRequest in which the exception occurred
     * @param ex      the exception to set attributes for
     */
    private void setRequestAttributes(final HttpServletRequest request, final Exception ex) {
        request.setAttribute("javax.servlet.error.exception", ex);
        request.setAttribute("javax.servlet.error.message", ex.getMessage());
        request.setAttribute("javax.servlet.error.request_uri", request.getRequestURI());
        request.setAttribute("javax.servlet.error.status_code", HttpStatus.BAD_REQUEST.value());
    }

}
