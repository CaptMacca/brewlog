package brews.exceptions;

public class MeasurementServiceException extends RuntimeException {

    public MeasurementServiceException() {
        super();
    }

    public MeasurementServiceException(String msg) {
        super(msg);
    }

    public MeasurementServiceException(Exception e) {
        super(e);
    }

    public MeasurementServiceException(String msg, Exception e) {
        super(msg,e);
    }
}
