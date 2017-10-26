package controller;

import javafx.scene.Scene;
import model.FitnessModel;
import ui.FitnessView;

public class FitnessController {

    private Scene view;

    //default constructor
    public FitnessController(){

    }

    public FitnessController(FitnessModel model, FitnessView view){

    }

    //Returns the scene for the triggered button event.
    public Scene updateView(){
        return getScene();
    }

    public Scene getScene(){
        return view;
    }

    public void setScene(Scene view){
        this.view = view;
    }
}
