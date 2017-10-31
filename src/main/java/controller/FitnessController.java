package controller;

import javafx.scene.Scene;

public class FitnessController {

    //Used to hold the scene information passed from the view.
    private Scene scene;

    public FitnessController(Scene scene){
        this.scene = scene;
    }

    public Scene updateView(){
        return scene;
    }

    public void setView(Scene scene){
        this.scene = scene;
    }



}
