package brews.services.exceptions;

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
