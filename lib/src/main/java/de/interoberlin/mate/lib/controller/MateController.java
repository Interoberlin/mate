package de.interoberlin.mate.lib.controller;

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
}
