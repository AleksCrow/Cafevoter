package com.voronkov.cafevoiter.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CafeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<CafeException> handleNotFoundException() {
        return new ResponseEntity<>(new CafeException("Обьект не найден в базе данных"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DontCanVoteException.class)
    protected ResponseEntity<CafeException> handleDontCanVoteException() {
        return new ResponseEntity<>(new CafeException("Время для голосования окончено"), HttpStatus.NOT_ACCEPTABLE);
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
