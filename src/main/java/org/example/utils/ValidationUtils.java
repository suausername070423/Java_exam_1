package org.example.utils;

import java.util.regex.Pattern;

public class ValidationUtils {
    private ValidationUtils() {}

    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern FULLNAME =
            Pattern.compile("^[\\p{L} ]+$");

    public static boolean isValidEmail(String s) {
        return s != null && EMAIL.matcher(s).matches();
    }
    public static boolean isValidPassword(String s) {
        return s != null && s.length() >= 6 && s.length() <= 12;
    }
    public static boolean isValidFullName(String s) {
        return s != null && FULLNAME.matcher(s.trim()).matches();
    }
}
