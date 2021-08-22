package exceptions;

public class UserNameDoesNotExistException extends Exception {
    public UserNameDoesNotExistException(String userName,String applicationOfficialName) {
        super("User name "+userName+" does not exist for "+applicationOfficialName);
    }
}
