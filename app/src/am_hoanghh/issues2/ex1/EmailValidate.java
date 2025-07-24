package issues2.ex1;

import java.util.regex.Pattern;

public class EmailValidate {
    public boolean isValid(String email) {
        // Regular expression to match valid email formats
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);

        return email != null && p.matcher(email).matches();
    }
}
