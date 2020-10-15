package brews.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
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
