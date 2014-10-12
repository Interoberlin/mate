package de.interoberlin.mate.lib.controller;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MateController {

    /**
     * Solution found at http://stackoverflow.com/questions/10308283/java-lang-noclassdeffounderror-with-all-r-classess-when-using-android-library
     * @param packageName
     * @param className
     * @param name
     * @return
     */
    public static int getResourseIdByName(String packageName, String className, String name) {
        int id = 0;
        try {
            for (int i = 0; i < Class.forName(packageName + ".R").getClasses().length; i++) {
                if(Class.forName(packageName + ".R").getClasses()[i].getName().split("\\$")[1].equals(className)) {
                    if(Class.forName(packageName + ".R").getClasses()[i] != null)
                        id = Class.forName(packageName + ".R").getClasses()[i].getField(name).getInt(Class.forName(packageName + ".R").getClasses()[i]);
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String getProperty(Context context, String key)
    {
        Properties props = new Properties();
        InputStream is = null;

        try {
            is = context.getAssets().open("gradle.properties");
            props.load(is);
            return props.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "property not found";
    }
}
