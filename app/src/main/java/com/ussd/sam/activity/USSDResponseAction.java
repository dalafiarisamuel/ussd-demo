package com.ussd.sam.activity;

public enum USSDResponseAction {

    CANCEL("CANCEL"),
    SEND("SEND"),
    OK("OK");

    private final String responseAction;

    USSDResponseAction(String responseAction) {
        this.responseAction = responseAction;
    }

    public String getResponseAction() {
        return responseAction;
    }
}
