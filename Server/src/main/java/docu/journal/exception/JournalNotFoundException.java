package docu.journal.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The page of data that you have requested could not be found")
public class JournalNotFoundException extends RuntimeException {

    public JournalNotFoundException(String message) {
        super(message);
    }
}
