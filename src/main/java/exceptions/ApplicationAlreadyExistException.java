package exceptions;

public class ApplicationAlreadyExistException extends Exception {
    public ApplicationAlreadyExistException(String officialName){
        super(officialName + " already Exist");
    }
}
