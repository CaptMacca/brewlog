package brews.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserPasswordMismatchException extends RuntimeException {

    public UserPasswordMismatchException() {
        super();
    }

    public UserPasswordMismatchException(String msg) {
        super(msg);
    }

    public UserPasswordMismatchException(Exception e) {
        super(e);
    }

    public UserPasswordMismatchException(String msg, Exception e) {
        super(msg,e);
    }
}
