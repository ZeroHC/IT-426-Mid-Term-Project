package launcher;


import model.FitnessModel;
import ui.FitnessView;

import static javafx.application.Application.launch;

public class Launcher {

    public static void main(String[] args) {

        FitnessModel model = new FitnessModel();
        FitnessView view = new FitnessView();
        view.getView();

        launch(FitnessView.class, args);
    }
}
