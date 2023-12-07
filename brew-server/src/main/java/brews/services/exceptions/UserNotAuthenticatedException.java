package brews.services.exceptions;

public class UserNotAuthenticatedException extends RuntimeException {

    public UserNotAuthenticatedException() {
        super();
    }

    public UserNotAuthenticatedException(String msg) {
        super(msg);
    }

    public UserNotAuthenticatedException(Exception e) {
        super(e);
    }

    public UserNotAuthenticatedException(String msg, Exception e) {
        super(msg,e);
    }
}
