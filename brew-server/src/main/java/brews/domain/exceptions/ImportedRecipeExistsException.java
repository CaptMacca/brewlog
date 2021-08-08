package brews.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
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
