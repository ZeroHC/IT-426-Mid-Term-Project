package model;

import java.io.File;

import static model.MakeDefaultXML.defaultXMl;

public class InitializeDefaultXMLData {

    private final static File defaultXMLData = new File(System.getProperty("user.dir") +
                                                        "/data/master.xml");

    public static void InitializeDefaultData(){

        try {

            if (defaultXMLData.getParentFile().mkdir() && defaultXMLData.createNewFile())
            {
                System.out.println("The file was created ^_^ ");
                defaultXMl(defaultXMLData.getAbsolutePath());
            }else {
                System.out.println("The file already exists...");
            }

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
