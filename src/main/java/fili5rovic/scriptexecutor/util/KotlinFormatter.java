package fili5rovic.scriptexecutor.util;

import com.facebook.ktfmt.format.Formatter;

public class KotlinFormatter {

    public static String format(String kotlinCode) {
        try {
            return Formatter.format(kotlinCode);
        } catch (Exception e) {
            System.err.println("Error formatting Kotlin code: " + e.getMessage());
            return kotlinCode;
        }
    }
}