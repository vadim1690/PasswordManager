package model;

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

public enum PasswordStrength {
    WEAK,MEDIUM,STRONG
}
