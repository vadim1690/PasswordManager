package exceptions;

public class IllegalPasswordException extends Exception {
    public IllegalPasswordException(){
        super("Empty Password");
    }
}
