package brews.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleDoesntExistException extends RuntimeException {

    public RoleDoesntExistException() {
        super();
    }

    public RoleDoesntExistException(String msg) {
        super(msg);
    }

    public RoleDoesntExistException(Exception e) {
        super(e);
    }

    public RoleDoesntExistException(String msg, Exception e) {
        super(msg,e);
    }

}
