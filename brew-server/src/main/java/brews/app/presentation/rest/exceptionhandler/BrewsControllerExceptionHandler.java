package brews.app.presentation.rest.exceptionhandler;

import brews.domain.exceptions.*;
import brews.util.transformer.beerxml.exception.BeerXMLParserException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class BrewsControllerExceptionHandler {

    @ExceptionHandler(BrewsEntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ErrorDetails> handleRecipeNotFoundException(BrewsEntityNotFoundException ex, WebRequest request) {
        log.error("Entity not found exception: ",ex);
        return buildResponse(ex, "Brew not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImportedRecipeExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ResponseEntity<ErrorDetails> handleImportedRecipeExistsException(ImportedRecipeExistsException ex, WebRequest request) {
        log.error("Duplicate imported recipe exception: ",ex);
        return buildResponse(ex, "Imported recipe already exists", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.error("Illegal argument exception: ",ex);
        return buildResponse(ex, "Illegal argument", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImportedRecipeUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorDetails> handleImportedRecipeUploadException(ImportedRecipeUploadException ex, WebRequest request) {
        log.error("Imported recipe could not be parsed and imported: ",ex);
        return buildResponse(ex, "Import recipe failure", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BeerXMLParserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorDetails> handleBeerXMLParseException(BeerXMLParserException ex, WebRequest request) {
        log.error("Imported recipe could not be parsed and imported: ",ex);
        return buildResponse(ex, "Recipe XML parsing failed", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleDoesntExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorDetails> handleRoleDoesntExistException(RoleDoesntExistException ex, WebRequest request) {
        log.error("Requested Role is not a valid one: ",ex);
        return buildResponse(ex, "Role does not exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorDetails> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex, WebRequest request) {
        log.error("User with userid has already been registered: ",ex);
        return buildResponse(ex, "Duplicate user registraion", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public final ResponseEntity<ErrorDetails> handleUserNotAuthenticatedException(UserNotAuthenticatedException ex, WebRequest request) {
        log.error("User with userid has not been authenticated: ",ex);
        return buildResponse(ex, "Authentication Failed", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Exception processing request: ",ex);
        return buildResponse(ex, "Fatal Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public final ResponseEntity<ErrorDetails> handleBadCredentialsExceptions(Exception ex, WebRequest request) {
        log.error("Exception processing request: ",ex);
        return buildResponse(ex, "Bad Credentials", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OptimisticEntityLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ResponseEntity<ErrorDetails> handleOptimisticEntityLockExceptions(Exception ex, WebRequest request) {
        log.error("Exception processing request: ",ex);
        List<String> messages = Arrays.asList("Data has been updated by another user");
        return buildResponse(ex, "Optimistic Lock Failure", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorDetails> handleMethodArgumentNotVlaidException(MethodArgumentNotValidException ex, WebRequest request) {
        //Get all errors
        List<String> errors = ex.getBindingResult()
          .getFieldErrors()
          .stream()
          .map(x -> x.getDefaultMessage())
          .collect(Collectors.toList());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
          errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorDetails> buildResponse(Exception ex, String error, HttpStatus httpStatus) {
        List<String> messages = Arrays.asList(ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), error,
          messages);
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

}
