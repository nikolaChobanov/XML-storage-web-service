package docu.journal.exception;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Log4j2
@ControllerAdvice
public class JournalExceptionController extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = JournalNotFoundException.class)
    public ResponseEntity<Object> handleJournalNotFoundException(JournalNotFoundException exception) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(exception.getMessage());
        errors.setStatusName(HttpStatus.NOT_FOUND);
        errors.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = TooManyUsersException.class)
    public ResponseEntity<Object> handleTooManyUsersException(TooManyUsersException exception) {
        logger.error("Too many users currently using controller access denied");

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(exception.getMessage());
        errors.setStatusName(HttpStatus.TOO_MANY_REQUESTS);
        errors.setStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());

        return new ResponseEntity<>(errors, HttpStatus.TOO_MANY_REQUESTS);
    }

/*
    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                 HttpHeaders headers,
                                 HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all fields errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }*/
}
