package launcher;

import controller.FitnessController;
import model.FitnessModel;
import ui.FitnessView;

import static javafx.application.Application.launch;

public class Launcher {

    public static void main(String[] args) {

        launch(FitnessView.class, args);
    }
}
