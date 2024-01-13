package pw;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author jenre
 */

public class PasswordGame implements StrongPassword {
    String password;

    public PasswordGame(String password) {
        this.password = password;
    }

    @Override
    public boolean isStrong() {
        /* Use regular expressions to check if password meets the strength criteria of
           at least 1 upper case letter, 1 lower case letter, 1 number, 1 special character, that is
           at least 8 characters long
        */
        Pattern pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
        
    }
}
