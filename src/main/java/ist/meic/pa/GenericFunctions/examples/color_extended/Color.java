package ist.meic.pa.GenericFunctions.examples.color_extended;

import ist.meic.pa.GenericFunctions.GenericFunction;

@GenericFunction
public class Color {
    public static String mix(Color c1, Color c2){
        return "a"; // mix(c2, c1);
    }
    public static String mix(Red c1, Red c2) {
        return "More red";
    }
    public static String mix(Blue c1, Blue c2) {
        return "More blue";
    }
    public static String mix(Yellow c1, Yellow c2) {
        return "More yellow";
    }
    public static String mix(Red c1, Blue c2) {
        return "Magenta";
    }
    public static String mix(Vermilion c1, Blue c2) {
        return "Magenta";
    }
    public static String mix(Red c1, NavyBlue c2) {
        return "Magenta";
    }
    public static String mix(Vermilion c1, NavyBlue c2) {
        return "Magenta";
    }
    public static String mix(Red c1, Yellow c2) {
        return "Orange";
    }
    public static String mix(Blue c1, Yellow c2){
        return "Green";
    }
    public static String mix(String c1, String c2) {
        return "lol";
    }
}
