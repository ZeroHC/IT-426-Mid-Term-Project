package ui;

import helper.FileReadingHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ScheduledHikes extends Application
{
    @Override
    public void start(Stage stage) {
        stage.setTitle("Hike Master 9000");
        stage.setScene(scheduledHikes());
        stage.show();
    }

    public static Scene scheduledHikes()
    {
        VBox scheduledHikesMenu = new VBox();

        Text header = new Text("Scheduled Hikes");
        scheduledHikesMenu.getChildren().add(header);

        ArrayList<String> hikes = FileReadingHelper.fileChecker();

        if (hikes.isEmpty())
        {
            Text error = new Text("No hike has been created!");
            scheduledHikesMenu.getChildren().add(error);
        }
        else
        {
            CheckBox[] boxes = new CheckBox[hikes.size()];

            //add our checkboxes
            for (int i = 0; i < hikes.size(); i++)
            {
                boxes[i] = new CheckBox(hikes.get(i));
            }

            scheduledHikesMenu.getChildren().addAll(boxes);

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    //deletes the selected checkboxes
                }
            });
        }

        Button doneButton = new Button("Done");
        doneButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //goes back to home scene
            }
        });

        scheduledHikesMenu.getChildren().add(doneButton);

        Scene scheduledHikesScene = new Scene(scheduledHikesMenu, 400, 600);
        scheduledHikesScene.getStylesheets().add("styles/hikeStyles.css");

        return scheduledHikesScene;
    }
}
