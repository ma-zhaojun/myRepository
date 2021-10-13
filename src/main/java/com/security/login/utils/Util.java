package com.security.login.utils;

public class Util {
    public static String getErrorMsgWithTrace(Exception ex) {
        return getErrorMsgWithTrace(ex, 0);
    }

    public static String getErrorMsgWithTrace(Exception ex, int maxLength) {
        StringBuilder sb = new StringBuilder(300);
        sb.append(ex.getMessage()).append("\n");
        StackTraceElement[] trace = ex.getStackTrace();
        for (int i = 0; i < 5; i++) {
            if (maxLength > 0 && sb.length() >= maxLength) {
                break;
            }
            sb.append(trace[i].toString()).append("\t\n");
        }
        return sb.toString();
    }
}
