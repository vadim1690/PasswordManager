package exceptions;

public class UserNameAlreadyExistException extends Exception {
    public UserNameAlreadyExistException(String userName,String applicationOfficialName) {
        super("User name "+userName+" already exist for "+applicationOfficialName);
    }
}
