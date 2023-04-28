package brews.services.exceptions;

public class ImportedRecipeUploadException extends RuntimeException {

    public ImportedRecipeUploadException() {
        super();
    }

    public ImportedRecipeUploadException(String msg) {
        super(msg);
    }

    public ImportedRecipeUploadException(Exception e) {
        super(e);
    }

    public ImportedRecipeUploadException(String msg, Exception e) {
        super(msg,e);
    }
}
