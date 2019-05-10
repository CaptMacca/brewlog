package brews.handler;

import brews.exceptions.ImportedRecipeExistsException;
import brews.exceptions.BrewsEntityNotFoundException;
import brews.exceptions.ImportedRecipeUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class BrewsControllerExceptionHandler {

    @ExceptionHandler(BrewsEntityNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleRecipeNotFoundException(BrewsEntityNotFoundException ex, WebRequest request) {
        log.error("Entity not found exception: ",ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImportedRecipeExistsException.class)
    public final ResponseEntity<ErrorDetails> handleImportedRecipeExistsException(ImportedRecipeExistsException ex, WebRequest request) {
        log.error("Duplicate imported recipe exception: ",ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.error("Illegal argument exception: ",ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImportedRecipeUploadException.class)
    public final ResponseEntity<ErrorDetails> handleImportedRecipeUploadException(ImportedRecipeUploadException ex, WebRequest request) {
        log.error("Imported recipe could not be parsed and imported: ",ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Exception processing request: ",ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
