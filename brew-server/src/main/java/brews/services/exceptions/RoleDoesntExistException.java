package brews.services.exceptions;

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
