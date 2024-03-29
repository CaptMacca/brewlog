package brews.services.exceptions;

public class ImportedRecipeExistsException extends RuntimeException {

    public ImportedRecipeExistsException() {
        super();
    }

    public ImportedRecipeExistsException(String msg) {
        super(msg);
    }

    public ImportedRecipeExistsException(Exception e) {
        super(e);
    }

    public ImportedRecipeExistsException(String msg, Exception e) {
        super(msg,e);
    }
}
