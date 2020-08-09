package brews.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserEntityNotFoundException extends RuntimeException {

    public UserEntityNotFoundException() {
        super();
    }

    public UserEntityNotFoundException(String msg) {
        super(msg);
    }

    public UserEntityNotFoundException(Exception e) {
        super(e);
    }

    public UserEntityNotFoundException(String msg, Exception e) {
        super(msg,e);
    }
}
