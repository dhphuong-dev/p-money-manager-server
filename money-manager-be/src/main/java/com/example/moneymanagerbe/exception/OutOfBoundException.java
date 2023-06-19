package com.example.moneymanagerbe.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class OutOfBoundException extends RuntimeException {

    private String message;

    private HttpStatus status;

    private String[] params;

    public OutOfBoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
        this.message = message;
    }

    public OutOfBoundException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public OutOfBoundException(String message, String[] params) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
        this.message = message;
        this.params = params;
    }

    public OutOfBoundException(HttpStatus status, String message, String[] params) {
        super(message);
        this.status = status;
        this.message = message;
        this.params = params;
    }
}
