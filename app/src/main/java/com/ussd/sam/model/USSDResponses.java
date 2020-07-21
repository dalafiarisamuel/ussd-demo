package com.ussd.sam.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ussd.sam.activity.USSDResponseAction;

import java.util.List;

public class USSDResponses implements Parcelable {

    public static final String TAG = "USSDResponses";

    public static final Creator<USSDResponses> CREATOR = new Creator<USSDResponses>() {
        @Override
        public USSDResponses createFromParcel(Parcel in) {
            return new USSDResponses(in);
        }

        @Override
        public USSDResponses[] newArray(int size) {
            return new USSDResponses[size];
        }
    };
    private List<String> ussdResponses;

    public USSDResponses() {
    }

    protected USSDResponses(Parcel in) {
        ussdResponses = in.createStringArrayList();
    }

    public List<String> getUssdRepsonses() {
        return ussdResponses;
    }

    public void setUssdResponses(List<String> ussdResponses) {
        this.ussdResponses = ussdResponses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(ussdResponses);
    }

    public String getNoticeResponse() {

        final StringBuilder builder = new StringBuilder();

        if (this.ussdResponses != null) {

            this.ussdResponses.remove(USSDResponseAction.OK.getResponseAction());

            for (String value : this.ussdResponses) {
                builder.append(value);
                builder.append(" ");
            }

        }

        return builder.toString().trim();
    }

    public boolean isInputResponse() {

        if (this.ussdResponses == null || this.ussdResponses.isEmpty()) return true;
        return this.ussdResponses.contains(USSDResponseAction.SEND.getResponseAction());
    }

    public boolean isNoticeResponse() {

        if (this.ussdResponses == null || this.ussdResponses.isEmpty()) return true;
        return this.ussdResponses.contains(USSDResponseAction.OK.getResponseAction());
    }

    public String getInputResponse() {

        final StringBuilder builder = new StringBuilder();

        if (this.ussdResponses != null) {

            this.ussdResponses.remove(USSDResponseAction.SEND.getResponseAction());
            this.ussdResponses.remove(USSDResponseAction.CANCEL.getResponseAction());

            for (String value : this.ussdResponses) {
                builder.append(value);
                builder.append(" ");
            }

        }

        return builder.toString().trim();
    }
}
