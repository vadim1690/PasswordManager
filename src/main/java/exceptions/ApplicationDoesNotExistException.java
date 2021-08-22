package exceptions;

public class ApplicationDoesNotExistException extends Exception {
    public ApplicationDoesNotExistException(String officialName) {
        super(officialName+" does not exist");
    }
}
