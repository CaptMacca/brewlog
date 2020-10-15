package brews.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BrewsEntityNotFoundException extends RuntimeException {

    public BrewsEntityNotFoundException() {
        super();
    }

    public BrewsEntityNotFoundException(String msg) {
        super(msg);
    }

    public BrewsEntityNotFoundException(Exception e) {
        super(e);
    }

    public BrewsEntityNotFoundException(String msg, Exception e) {
        super(msg,e);
    }
}
