package pl.com.kocielapki.cattery.cattery.logic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {

    public static void validateEmail(String email) {
        if(!isEmailValid(email)) {
            throw new IllegalArgumentException("Niepoprawny e-mail!");
        }
    }

    private static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
