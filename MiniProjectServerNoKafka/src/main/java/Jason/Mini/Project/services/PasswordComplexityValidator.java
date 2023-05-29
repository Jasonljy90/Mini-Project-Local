package Jason.Mini.Project.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class PasswordComplexityValidator {

    public static boolean isValid(final String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}

/*
 * Password must contain at least one digit [0-9].
 * Password must contain at least one lowercase Latin character [a-z].
 * Password must contain at least one uppercase Latin character [A-Z].
 * Password must contain at least one special character like ! @ # & ( ).
 * Password must contain a length of at least 8 characters and a maximum of 20
 * characters.
 */
