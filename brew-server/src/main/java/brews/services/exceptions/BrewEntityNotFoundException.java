package brews.services.exceptions;

public class BrewEntityNotFoundException extends RuntimeException {

    public BrewEntityNotFoundException() {
        super();
    }

    public BrewEntityNotFoundException(String msg) {
        super(msg);
    }

    public BrewEntityNotFoundException(Exception e) {
        super(e);
    }

    public BrewEntityNotFoundException(String msg, Exception e) {
        super(msg,e);
    }
}
