package model;

import java.io.File;

import static model.MakeDefaultXML.defaultXMl;

import static model.ValidateOS.isMac;
import static model.ValidateOS.isWindows;

public class InitializeDefaultXMLData {

    private static File defaultXMLData = null;

    //private final static String fileName = "/Data/master.xml"; //if you want to use the commented version, be sure to uncomment this
    private final static String fileName = "/master.xml";
    private final static String folderName = "/data";

    private final static String file = "/data/master.xml";

    public static void InitializeDefaultData(){

        try {
            if(isWindows()){
                defaultXMLData = new File(System.getProperty("user.dir") + folderName);

                if(!defaultXMLData.exists()){
                    defaultXMLData.mkdir();
                }

                defaultXMLData = new File(defaultXMLData + fileName);

            }else if (isMac()){
                defaultXMLData = new File(System.getProperty("user.dir") + file);
            }

            if (defaultXMLData.createNewFile())
            {
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
