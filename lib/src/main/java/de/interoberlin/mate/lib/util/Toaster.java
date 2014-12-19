package de.interoberlin.mate.lib.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

public class Toaster {
    private static List<String> toasts;
    private static Activity activity;
    private static Context context;

    /**
     * Adds new toast
     *
     * @param message
     */
    public static void add(final String message) {
        if (activity != null && context != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT);
                }
            });
        }
    }

    /**
     * Registers activity to display toast on
     *
     * @param activity
     * @param context
     */
    public static void register(Activity activity, Context context) {
        Toaster.activity = activity;
        Toaster.context = context;
    }

    /**
     * Unregisters activity to display toast on
     */
    public static void unregister() {
        Toaster.activity = null;
        Toaster.context = null;
    }
}

