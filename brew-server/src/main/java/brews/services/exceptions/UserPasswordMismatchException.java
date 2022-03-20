package brews.services.exceptions;

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
