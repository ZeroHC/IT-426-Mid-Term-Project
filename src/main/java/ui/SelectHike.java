package ui;

import helper.FileReadingHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;


public class SelectHike extends Application
{
    @Override
    public void start(Stage stage) {
        stage.setTitle("Hike Master 9000");
        stage.setScene(selectHike());
        stage.show();
    }

    public static Scene selectHike()
    {
        VBox selectHikeMenu = new VBox();

        Text header = new Text("Select Hike");
        selectHikeMenu.getChildren().add(header);


        HBox buttonRow = new HBox();
        buttonRow.setId("buttonRow");

        Button backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //goes back to home scene
            }
        });

        Button nextButton = new Button("Next");
        nextButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //goes to the next scene
            }
        });


        buttonRow.getChildren().addAll(backButton, nextButton);

        selectHikeMenu.getChildren().add(buttonRow);

        Scene selectHikeScene = new Scene(selectHikeMenu, 400, 600);
        selectHikeScene.getStylesheets().add("styles/hikeStyles.css");

        return selectHikeScene;
    }
}
