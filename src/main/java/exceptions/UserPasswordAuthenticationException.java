package exceptions;

public class UserPasswordAuthenticationException extends Exception {
    public UserPasswordAuthenticationException(String userName, String officialName) {
    super("Password entered for user "+userName+" for "+officialName+" does not match");
    }
}
