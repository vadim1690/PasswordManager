package utilities;

import model.PasswordStrength;

import java.util.regex.Pattern;

/*
        password strength categories

            Strong:     Password must contain at least one digit [0-9],
                        Password must contain at least one lowercase Latin character [a-z],
                        Password must contain at least one uppercase Latin character [A-Z],
                        Password must contain at least one special character [!@#&()–[{}]:;',?/*~$^+=<>],
                        Password must contain a length of at least 8 characters and a maximum of 32 characters.

            Medium:     Password must contain at least one digit [0-9],
                        Password must contain at least one lowercase Latin character [a-z],
                        Password must contain at least one uppercase Latin character [A-Z],
                        Password must contain a length of at least 8 characters and a maximum of 32 characters.

            Weak:       Password that contains less than 8 or more than 32 Symbols and does not contain at least 1 Capital letter [A-Z].


     */


public class PasswordRegex {

    public static final String PASSWORD_CATEGORIES_INFORMATION = "Password strength categories\n" +
            "\n" +
            "Strong:     \n" +
            "          Password must contain at least one digit [0-9],\n" +
            "          Password must contain at least one lowercase Latin character [a-z],\n" +
            "          Password must contain at least one uppercase Latin character [A-Z],\n" +
            "          Password must contain at least one special character [!@#&()–[{}]:;',?/*~$^+=<>],\n" +
            "          Password must contain a length of at least 8 characters and a maximum of 32 characters.\n" +
            "\n" +
            "Medium:     \n" +
            "          Password must contain at least one digit [0-9],\n" +
            "          Password must contain at least one lowercase Latin character [a-z],\n" +
            "          Password must contain at least one uppercase Latin character [A-Z],\n" +
            "          Password must contain a length of at least 8 characters and a maximum of 32 characters.\n" +
            "\n" +
            "Weak:       \n" +
            "          Password that contains less than 8 or more than 32 Symbols\n" +
            "          and does not contain at least 1 Capital letter [A-Z].";

    private static final String REGEX_STRONG_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,32}$";
    private static final Pattern STRONG_PATTERN = Pattern.compile(REGEX_STRONG_PASSWORD);

    private static final String REGEX_MEDIUM_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$";
    private static final Pattern MEDIUM_PATTERN = Pattern.compile(REGEX_MEDIUM_PASSWORD);


    public static PasswordStrength computePassword(String password){
        if (STRONG_PATTERN.matcher(password).matches())
            return PasswordStrength.STRONG;
        if(MEDIUM_PATTERN.matcher(password).matches())
            return PasswordStrength.MEDIUM;
        return PasswordStrength.WEAK;
    }

}
