package pw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrongerPassword implements StrongPassword {
    private String password;

    public StrongerPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isStrong() {
        /* Use regular expressions to check if password meets the strength criteria of
           at least 2 upper case letter, 2 lower case letter, 3 numbers, 3 special character, that is
           at least 12 characters long
        */
        Pattern pattern = Pattern.compile("(?=.*[a-z].*[a-z])(?=.*[A-Z].*[A-Z])(?=.*\\d.*\\d.*\\d)(?=.*[^\\w\\s].*[^\\w\\s].*[^\\w\\s]).{12,}");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}