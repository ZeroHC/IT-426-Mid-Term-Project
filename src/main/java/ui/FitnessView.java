package ui;

import controller.FitnessController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FitnessView extends Application {

    private FitnessController controller = new FitnessController();
    private Scene view;

    public FitnessView(){
        view = home();
    }

    public Scene getView(){
        return this.view;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hike Master 9000");
        stage.setScene();
        stage.show();

    }

    //Default starting screen.
    Scene home(){

        VBox menuContainer = new VBox();
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setSpacing(10);

        return new Scene(menuContainer, 300, 500);
    }

//    //To do later
//    Scene newTrail(){
//
//    }
//
//    Scene checkList(){
//
//    }
//
//    Scene exerciseTracker(){
//
//    }
//
//    Scene scheduledHikes(){
//
//    }
//
//    Scene reminderMessages(){
//
//    }
//
//    Scene previousHike(){
//
//    }
//
//    Scene exerciseProgress(){
//
//    }
}
