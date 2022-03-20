package brews.services.exceptions;

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
