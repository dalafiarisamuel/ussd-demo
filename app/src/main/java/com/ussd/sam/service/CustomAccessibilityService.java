package com.ussd.sam.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import com.ussd.sam.model.USSDResponses;

import java.util.ArrayList;
import java.util.List;


public class CustomAccessibilityService extends AccessibilityService {

    private static final String TAG = "CustomAccessibility";


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        List<String> responseList = new ArrayList<>();


        if (event.getClassName().equals("android.app.AlertDialog")) {
            performGlobalAction(GLOBAL_ACTION_BACK);


            for (CharSequence sq : event.getText()) {

                //Log.d(TAG, "onAccessibilityEvent: " + sq.toString());
                responseList.add(sq.toString());
            }

            USSDResponses responses = new USSDResponses();
            responses.setUssdResponses(responseList);

            Intent intent = new Intent("com.ussd.sam.action.REFRESH");
            intent.putExtra(USSDResponses.TAG, responses);
            sendBroadcast(intent);
            // write a broad cast receiver and call sendbroadcast() from here, if you want to parse the message for balance, date
        }

    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.packageNames = new String[]{"com.android.phone"};
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }
}
