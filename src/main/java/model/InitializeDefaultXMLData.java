package model;

import java.io.File;

import static model.MakeDefaultXML.defaultXMl;

import static model.ValidateOS.isMac;
import static model.ValidateOS.isWindows;

public class InitializeDefaultXMLData {

    private static File defaultXMLData = null;
    private final static String windowsPath = "c:\\HikeMaster9000\\master.xml";
    private static String macPath = "";
    private final static String fileName = "/Data/master.xml";

    public static void InitializeDefaultData(){

        try {

            if(isWindows()){
                defaultXMLData = new File(windowsPath);
            }else if (isMac()){
                String userDirectory = System.getProperty("user.dir");
                macPath = userDirectory + fileName;
                defaultXMLData = new File(macPath);
            }

            if(defaultXMLData.createNewFile()){
                System.out.println("The file was created ^_^ ");
                defaultXMl(defaultXMLData.getAbsolutePath());
            }else {
                System.out.println("the file already exists...");
            }

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
