/*
* Daniel Capps, Joshua Hawks, Hanchen Liu
* 11/06/2017
* Launcher.java
* This file launches the application.
*
*/

package launcher;

import ui.FitnessView;

import static javafx.application.Application.launch;
import static model.InitializeDefaultXMLData.InitializeDefaultData;

/**
 * This class launches the application and initializes default data if the file doesn't
 * exist on the current system.
 *
 * @author Daniel Capps, Joshua Hawks, Hanchen Liu
 * @version 1.0
 */
public class Launcher
{
    /**
     * This is the main program.
     *
     * @param args This parameter passes in arguments to be executed by main.
     */
    public static void main(String[] args)
    {
        //Ensures that the xml file is there and if not makes it.
        InitializeDefaultData();

        //Launches the application.
        launch(FitnessView.class, args);
    }
}
