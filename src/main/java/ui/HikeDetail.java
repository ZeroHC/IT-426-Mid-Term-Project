package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class HikeDetail extends Application
{
    @Override
    public void start(Stage stage) {
        stage.setTitle("Hike Master 9000");
        stage.setScene(hikeDetail());
        stage.show();
    }

    public static Scene hikeDetail()
    {
        VBox newHikeMenu = new VBox();

        Text header = new Text("Hike Detail");

        HBox locationRow = new HBox();

        Label hikeLocation = new Label("Location:\t");
        TextField locationInput = new TextField();

        locationRow.getChildren().addAll(hikeLocation, locationInput);

        HBox dateRow = new HBox();

        Label hikeDate = new Label("Date: \t");
        DatePicker dateInput = new DatePicker();

        dateRow.getChildren().addAll(hikeDate, dateInput);

        HBox buttonRow = new HBox();
        buttonRow.setId("buttonRow");

        Button backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                locationInput.clear();
                dateInput.setValue(null);

                //then goes back to home scene
            }
        });

        Button nextButton = new Button("Next");
        nextButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //writes all input to a history file
                File hikeHistory = new File("data/hikeHistory.txt");
                try
                {
                    hikeHistory.getParentFile().mkdir();
                    hikeHistory.createNewFile();
                }
                catch (IOException e)
                {
                    System.out.println(e.getMessage());
                }

                try(FileWriter fw = new FileWriter(hikeHistory, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw))
                {
                    out.println("Location: " + locationInput.getText());
                    out.println("Date: " + dateInput.getValue());
                    out.println();
                } catch (IOException e) {
                    //exception handling left as an exercise for the reader
                    System.out.println(e.getMessage());
                }

                //then goes to Reminder scene
            }
        });

        buttonRow.getChildren().addAll(backButton, nextButton);

        newHikeMenu.getChildren().addAll(header, locationRow, dateRow, buttonRow);

        Scene newHikeScene = new Scene(newHikeMenu, 360, 500);
        newHikeScene.getStylesheets().add("styles/hikeStyles.css");

        return newHikeScene;
    }
}
