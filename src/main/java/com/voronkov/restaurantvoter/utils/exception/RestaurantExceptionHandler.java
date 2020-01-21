package com.voronkov.restaurantvoter.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestaurantExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DontCanVoteException.class)
    protected ResponseEntity<CafeException> handleDontCanVoteException() {
        return new ResponseEntity<>(new CafeException("Voting time is over"), HttpStatus.NOT_ACCEPTABLE);
    }

    private static class CafeException {
        private String message;

        public CafeException(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
