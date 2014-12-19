package de.interoberlin.mate.lib.util;


public class LoremIpsum {

    public static String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

    // --------------------
    // Methods
    // --------------------

    public static String getSymbols(int i) {
        String s = "";

        while (s.length() < i) {
            s += LOREM_IPSUM;
        }

        return s.substring(0, i);
    }
}
