package pw;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserPassword extends PasswordGame{
    private int points;
    private int length;
    private String status;

    public UserPassword(String password, int length) {
        super(password);
        this.length = length;
        points = 0;
        status = "";
    }

    @Override
    public boolean isStrong() {
        // Use regular expressions to check if password meets strength criteria based on length
        String regex;
        if (length == 8) {
            regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}";
        } else if (length == 10) {
            regex = "(?=.*[a-z].*[a-z])(?=.*[A-Z].*[A-Z])(?=.*\\d.*\\d)(?=.*[^\\w\\s].*[^\\w\\s]).{10,}";
        } else {
            regex = "(?=.*[a-z].*[a-z])(?=.*[A-Z].*[A-Z])(?=.*\\d.*\\d.*\\d)(?=.*[^\\w\\s].*[^\\w\\s].*[^\\w\\s]).{12,}";
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void checkPasswordStrength() {
        if (isStrong()) {
            points++;
            status = "Password is strong. You have earned 1 point.";
        } else {
            status = "Password is not strong.";
        }
    }

    public String getStatus() {
        return status;
    }
    
    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }
    
}
