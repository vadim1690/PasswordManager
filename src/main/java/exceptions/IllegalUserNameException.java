package exceptions;

public class IllegalUserNameException extends Exception {

    public IllegalUserNameException() {
        super("Empty Username");
    }
}
