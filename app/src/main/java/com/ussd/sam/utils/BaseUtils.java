package com.ussd.sam.utils;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import java.util.List;

public class BaseUtils {


    public final static int SETTINGS_REQUEST_CODE = 123;
    public final static int ACCESSIBILITY_REQUEST_CODE = 124;

    public static boolean isAccessibilityEnabled(Context context) {

        AccessibilityManager am = (AccessibilityManager)
                context.getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = am
                .getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        for (AccessibilityServiceInfo service : runningServices) {
            if ("com.ussd.sam/.service.CustomAccessibilityService".equals(service.getId())) {
                return true;
            }
        }

        return false;
    }

    public static void logInstalledAccessiblityServices(Context context) {

        AccessibilityManager am = (AccessibilityManager) context
                .getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = am
                .getInstalledAccessibilityServiceList();
        for (AccessibilityServiceInfo service : runningServices) {
            System.out.println("@#@#@#@#@" + service.getId());
        }
    }


    public static void openAccessibilitySettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        activity.startActivityForResult(intent, ACCESSIBILITY_REQUEST_CODE);
    }


    public static void openAppSettings(Activity activity) {
        Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(i, SETTINGS_REQUEST_CODE);
    }

    public static void showToast(Context context,
                                 String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void sendUSSDRequest(Activity activity,
                                       String ussd) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(ussdToCallableUri(ussd));
        activity.startActivity(intent);
    }

    private static Uri ussdToCallableUri(String ussd) {

        StringBuilder uriString = new StringBuilder();

        if (!ussd.startsWith("tel:"))
            uriString.append("tel:");

        for (char c : ussd.toCharArray()) {

            if (c == '#')
                uriString.append(Uri.encode("#"));
            else
                uriString.append(c);
        }

        return Uri.parse(uriString.toString());
    }
}
