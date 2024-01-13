package pw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MediumPassword implements StrongPassword {
    private String password;

    public MediumPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isStrong() {
        /* Use regular expressions to check if password meets the strength criteria of
           at least 2 upper case letter, 2 lower case letter, 2 numbers, 2 special character, that is
           at least 10 characters long
        */
        Pattern pattern = Pattern.compile("(?=.*[a-z].*[a-z])(?=.*[A-Z].*[A-Z])(?=.*\\d.*\\d)(?=.*[^\\w\\s].*[^\\w\\s]).{10,}");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}



