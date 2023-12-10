package brews.app.presentation.rest.exceptionhandler;

import brews.services.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class BrewsControllerExceptionHandler {

    @ExceptionHandler(BrewEntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleBrewNotFoundException(BrewEntityNotFoundException ex, WebRequest request) {
        log.error("Entity not found exception: ",ex);
        return buildResponse(ex, "Brew not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecipeEntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleRecipeNotFoundException(RecipeEntityNotFoundException ex, WebRequest request) {
        log.error("Entity not found exception: ",ex);
        return buildResponse(ex, "Recipe not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImportedRecipeExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleImportedRecipeExistsException(ImportedRecipeExistsException ex, WebRequest request) {
        log.error("Duplicate imported recipe exception: ",ex);
        return buildResponse(ex, "Imported recipe already exists", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.error("Illegal argument exception: ",ex);
        return buildResponse(ex, "Illegal argument", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImportedRecipeUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleImportedRecipeUploadException(ImportedRecipeUploadException ex, WebRequest request) {
        log.error("Imported recipe could not be parsed and imported: ",ex);
        return buildResponse(ex, "Import recipe failure", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BeerXMLParserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleBeerXMLParseException(BeerXMLParserException ex, WebRequest request) {
        log.error("Imported recipe could not be parsed and imported: ",ex);
        return buildResponse(ex, "Recipe XML parsing failed", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleDoesntExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleRoleDoesntExistException(RoleDoesntExistException ex, WebRequest request) {
        log.error("Requested Role is not a valid one: ",ex);
        return buildResponse(ex, "Role does not exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex, WebRequest request) {
        log.error("User with userid has already been registered: ",ex);
        return buildResponse(ex, "Duplicate user registraion", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleUserNotAuthenticatedException(UserNotAuthenticatedException ex, WebRequest request) {
        log.error("User with userid has not been authenticated: ",ex);
        return buildResponse(ex, "Authentication Failed", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Exception processing request: ",ex);
        return buildResponse(ex, "Fatal Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleBadCredentialsExceptions(Exception ex, WebRequest request) {
        log.error("Exception processing request: ",ex);
        return buildResponse(ex, "Bad Credentials", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OptimisticEntityLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleOptimisticEntityLockExceptions(Exception ex, WebRequest request) {
        log.error("Exception processing request: ",ex);
        List<String> messages = Arrays.asList("Data has been updated by another user");
        return buildResponse(ex, "Optimistic Lock Failure", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        //Get all errors
        List<String> errors = ex.getBindingResult()
          .getFieldErrors()
          .stream()
          .map(x -> x.getDefaultMessage())
          .collect(Collectors.toList());

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Validation Failed", errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorDetails> buildResponse(Exception ex, String error, HttpStatus httpStatus) {
        List<String> messages = Collections.singletonList(ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), error, messages);
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

}
