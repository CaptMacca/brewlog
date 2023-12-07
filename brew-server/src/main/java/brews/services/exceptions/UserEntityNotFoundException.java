package brews.services.exceptions;

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
