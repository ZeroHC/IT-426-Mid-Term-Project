package launcher;

import ui.FitnessView;

import static javafx.application.Application.launch;
import static model.InitializeDefaultXMLData.InitializeDefaultData;

public class Launcher {

    public static void main(String[] args) {

        InitializeDefaultData();
        launch(FitnessView.class, args);
    }
}
