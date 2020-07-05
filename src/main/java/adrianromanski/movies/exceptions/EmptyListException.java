package adrianromanski.movies.exceptions;

public class EmptyListException extends RuntimeException {

    public EmptyListException() {
        super("Operation on Empty list");
    }
}
