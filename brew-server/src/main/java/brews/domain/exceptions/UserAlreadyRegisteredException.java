package brews.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException() {
        super();
    }

    public UserAlreadyRegisteredException(String msg) {
        super(msg);
    }

    public UserAlreadyRegisteredException(Exception e) {
        super(e);
    }

    public UserAlreadyRegisteredException(String msg, Exception e) {
        super(msg,e);
    }

}
