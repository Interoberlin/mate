package de.interoberlin.mate.lib.model;

import java.util.Calendar;

public class LogEntry {
    private String timeStamp;
    private ELog logLevel;
    private String tag;
    private String message;

    // --------------------
    // Constructors
    // --------------------

    public LogEntry(ELog logLevel, String tag, String message) {
        String hours = String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        String minutes = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
        String seconds = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
        String millis = String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND));

        this.timeStamp = addLeadingZeros(hours, 2) + ":" + addLeadingZeros(minutes, 2) + ":" + addLeadingZeros(seconds, 2) + "." + addLeadingZeros(millis, 3);
        this.logLevel = logLevel;
        this.tag = tag;
        this.message = message;
    }

    // --------------------
    // Methods
    // --------------------

    private String addLeadingZeros(String s, int digits) {
        while (s.length() < digits) {
            s = "0".concat(s);
        }

        return s;
    }

    // --------------------
    // Getters / Setters
    // --------------------

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ELog getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(ELog logLevel) {
        this.logLevel = logLevel;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}