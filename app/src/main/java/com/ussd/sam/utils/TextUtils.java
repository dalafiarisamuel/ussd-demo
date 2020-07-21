package com.ussd.sam.utils;

import android.content.Context;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

    public static String getString(Context context, String name) {
        int nameResourceID = context.getResources().getIdentifier(name, "string", context.getApplicationInfo().packageName);
        return context.getString(nameResourceID);
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public static boolean isEmpty(List list) {
        return (list == null || list.isEmpty());
    }

    public static String removeTags(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }

        Matcher m = REMOVE_TAGS.matcher(string);
        return m.replaceAll("");
    }


}
