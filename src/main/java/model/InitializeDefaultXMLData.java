/*
* Daniel Capps, Joshua Hawks, Hanchen Liu
* 11/06/2017
* InitializeDefaultXMLData.java
* This file checks to see if the file and directory of the file are needed to be created.
*/

package model;

import java.io.File;

import static model.MakeDefaultXML.defaultXMl;

/**
 * This class is used to check if the directory and file exist and if not, then it
 * proceeds to make that directory and file.
 *
 * @author Hanchen Liu, Joshua Hawks
 * @version 1.0
 */
public class InitializeDefaultXMLData
{

    //The path that the file should be made in.
    private final static File defaultXMLData = new File(System.getProperty("user.dir") + "/data/master.xml");

    /**
     * A method that checks to see if the directory and file are present in the correct path.
     */
    public static void InitializeDefaultData()
    {
        try
        {
            //
            if (defaultXMLData.getParentFile().mkdir() && defaultXMLData.createNewFile())
            {
                System.out.println("The file was created ^_^ ");

                //Method call to make the xml document based on the path provided.
                defaultXMl(defaultXMLData.getAbsolutePath());
            }
            else
            {
                System.out.println("The file already exists...");
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
