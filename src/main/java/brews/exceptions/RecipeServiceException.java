package brews.exceptions;

public class RecipeServiceException extends RuntimeException {

    public RecipeServiceException() {
        super();
    }

    public RecipeServiceException(String msg) {
        super(msg);
    }

    public RecipeServiceException(Exception e) {
        super(e);
    }

    public RecipeServiceException(String msg, Exception e) {
        super(msg,e);
    }
}
