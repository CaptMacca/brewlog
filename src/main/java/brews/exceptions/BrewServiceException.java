package brews.exceptions;

public class BrewServiceException extends RuntimeException {

    public BrewServiceException() {
        super();
    }

    public BrewServiceException(String msg) {
        super(msg);
    }

    public BrewServiceException(Exception e) {
        super(e);
    }

    public BrewServiceException(String msg, Exception e) {
        super(msg,e);
    }
}
