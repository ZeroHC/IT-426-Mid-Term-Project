package model;

public class ValidateOS {

    private static String machineOS = System.getProperty("os.name").toLowerCase();

    public static  boolean isWindows(){
        return (machineOS.indexOf("win") >= 0);
    }

    public static boolean isMac(){
        return (machineOS.indexOf("mac") >= 0);
    }
}
