package brews.exceptions;

public class ImportRecipeServiceException extends RuntimeException {

    public ImportRecipeServiceException() {
        super();
    }

    public ImportRecipeServiceException(String msg) {
        super(msg);
    }

    public ImportRecipeServiceException(Exception e) {
        super(e);
    }

    public ImportRecipeServiceException(String msg, Exception e) {
        super(msg,e);
    }
}
