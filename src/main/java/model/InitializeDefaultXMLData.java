package model;

import java.io.File;

import static model.MakeDefaultXML.defaultXMl;

import static model.ValidateOS.isMac;
import static model.ValidateOS.isWindows;

public class InitializeDefaultXMLData {

    private static File defaultXMLData = null;
    private final static String windowsPath = "data/master.xml";
    private static String macPath = "";
    //private final static String fileName = "/Data/master.xml"; //if you want to use the commented version, be sure to uncomment this
    private final static String fileName = "data/master.xml";

    public static void InitializeDefaultData(){

        try {
            defaultXMLData = new File(fileName);
            if (defaultXMLData.getParentFile().mkdir() && defaultXMLData.createNewFile())
            {
                System.out.println("The file was created ^_^ ");
                defaultXMl(defaultXMLData.getAbsolutePath());
            }else {
                System.out.println("the file already exists...");
            }

            /*if(isWindows()){
                defaultXMLData = new File(windowsPath);
            }else if (isMac()){
                String userDirectory = System.getProperty("user.dir");
                macPath = userDirectory + fileName;
                defaultXMLData = new File(macPath);
            }

            if (defaultXMLData.getParentFile().mkdir() && defaultXMLData.createNewFile())
            {
                System.out.println("The file was created ^_^ ");
                defaultXMl(defaultXMLData.getAbsolutePath());
            }else {
                System.out.println("the file already exists...");
            }*/

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
