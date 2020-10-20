package brews.app.presentation.rest.exceptionhandler;

import brews.domain.exceptions.*;
import brews.util.transformer.beerxml.exception.BeerXMLParserException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
        return buildResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImportedRecipeExistsException.class)
    public final ResponseEntity<ErrorDetails> handleImportedRecipeExistsException(ImportedRecipeExistsException ex, WebRequest request) {
        log.error("Duplicate imported recipe exception: ",ex);
        return buildResponse(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.error("Illegal argument exception: ",ex);
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImportedRecipeUploadException.class)
    public final ResponseEntity<ErrorDetails> handleImportedRecipeUploadException(ImportedRecipeUploadException ex, WebRequest request) {
        log.error("Imported recipe could not be parsed and imported: ",ex);
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BeerXMLParserException.class)
    public final ResponseEntity<ErrorDetails> handleBeerXMLParseException(BeerXMLParserException ex, WebRequest request) {
        log.error("Imported recipe could not be parsed and imported: ",ex);
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleDoesntExistException.class)
    public final ResponseEntity<ErrorDetails> handleRoleDoesntExistException(RoleDoesntExistException ex, WebRequest request) {
        log.error("Requested Role is not a valid one: ",ex);
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public final ResponseEntity<ErrorDetails> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex, WebRequest request) {
        log.error("User with userid has already been registered: ",ex);
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotAuthenticatedException(UserNotAuthenticatedException ex, WebRequest request) {
        log.error("User with userid has already been registered: ",ex);
        return buildResponse(ex, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Exception processing request: ",ex);
        return buildResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ErrorDetails> handleBadCredentialsExceptions(Exception ex, WebRequest request) {
        log.error("Exception processing request: ",ex);
        return buildResponse(ex, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OptimisticEntityLockException.class)
    public final ResponseEntity<ErrorDetails> handleOptimisticEntityLockExceptions(Exception ex, WebRequest request) {
        log.error("Exception processing request: ",ex);
        return buildResponse(ex, request, HttpStatus.CONFLICT);
    }

    private ResponseEntity<ErrorDetails> buildResponse(Exception ex, WebRequest request, HttpStatus httpStatus) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
          request.getDescription(false));
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

}
