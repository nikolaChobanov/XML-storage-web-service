package docu.journal.exception;

//@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS, reason = "Server is currently overused please try again later")
public class TooManyUsersException extends RuntimeException {

    public TooManyUsersException(String message) {
        super(message);
    }
}
