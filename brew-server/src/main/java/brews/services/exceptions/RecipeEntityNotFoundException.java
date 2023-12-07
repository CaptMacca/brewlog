package brews.services.exceptions;

public class RecipeEntityNotFoundException extends RuntimeException {

    public RecipeEntityNotFoundException() {
        super();
    }

    public RecipeEntityNotFoundException(String msg) {
        super(msg);
    }

    public RecipeEntityNotFoundException(Exception e) {
        super(e);
    }

    public RecipeEntityNotFoundException(String msg, Exception e) {
        super(msg,e);
    }
}
