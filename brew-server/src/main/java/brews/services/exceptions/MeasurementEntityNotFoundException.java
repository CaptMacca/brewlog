package brews.services.exceptions;

public class MeasurementEntityNotFoundException extends RuntimeException {

    public MeasurementEntityNotFoundException() {
        super();
    }

    public MeasurementEntityNotFoundException(String msg) {
        super(msg);
    }

    public MeasurementEntityNotFoundException(Exception e) {
        super(e);
    }

    public MeasurementEntityNotFoundException(String msg, Exception e) {
        super(msg,e);
    }
}
