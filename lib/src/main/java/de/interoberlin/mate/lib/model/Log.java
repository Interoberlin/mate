package de.interoberlin.mate.lib.model;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private static List<LogEntry> log = new ArrayList<>();
    private static int buffer = 100;

    // --------------------
    // Constructors
    // --------------------

    public Log() {
    }

    // --------------------
    // Methods
    // --------------------

    public static void add(LogEntry logEntry) {
        log.add(logEntry);
    }

    private static void add(ELog logLevel, String tag, String message) {
        synchronized (log) {
            while (log.size() > buffer) {
                log.remove(0);
            }

            log.add(new LogEntry(logLevel, tag, message));
        }
    }

    /**
     * Adds a verbose log entry
     * @param tag tag
     * @param message message
     */
    public static void v(String tag, String message) {
        android.util.Log.v(tag, message);
        add(ELog.VERBOSE, tag, message);
    }

    /**
     * Adds a debug log entry
     * @param tag tag
     * @param message message
     */
    public static void d(String tag, String message) {
        android.util.Log.d(tag, message);
        add(ELog.DEBUG, tag, message);
    }

    /**
     * Adds a info log entry
     * @param tag tag
     * @param message message
     */
    public static void i(String tag, String message) {
        android.util.Log.i(tag, message);
        add(ELog.INFO, tag, message);
    }

    /**
     * Adds a warn log entry
     * @param tag tag
     * @param message message
     */
    public static void w(String tag, String message) {
        android.util.Log.w(tag, message);
        add(ELog.WARN, tag, message);
    }

    /**
     * Adds a error log entry
     * @param tag tag
     * @param message message
     */
    public static void e(String tag, String message) {
        android.util.Log.e(tag, message);
        add(ELog.ERROR, tag, message);
    }

    /**
     * Adds a wtf log entry
     * @param tag tag
     * @param message message
     */
    public static void wtf(String tag, String message) {
        android.util.Log.wtf(tag, message);
        add(ELog.WTF, tag, message);
    }

    public static List<LogEntry> getAll() {
            return log;
    }

    public static void clear() {
        log.clear();
    }
}
